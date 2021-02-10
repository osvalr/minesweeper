package com.osvalr.minesweeper.domain;

import com.google.common.base.Joiner;

import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Entity
public class Game extends BaseEntity {

    private Date startTime;
    private Date endTime;
    @Column(columnDefinition = "LONGVARCHAR")
    private String field;
    private int size;
    private GameState state;

    public Game() {
    }

    public Game(@Nonnull GameSize gameSize) {
        this.field = FieldConverter.toJson(getNewField(gameSize.getSize()));
        this.startTime = new Date();
        this.size = gameSize.getSize();
        this.state = GameState.IN_PROGRESS;
    }

    private Position[][] getNewField(int size) {
        Position[][] field = new Position[size][size];
        for (int i = 0; i < size; ++i)
            for (int j = 0; j < size; ++j)
                field[i][j] = new Position(new Random().nextDouble() < 0.1);
        return field;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public int getSize() {
        return size;
    }

    public GameState getState() {
        return state;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    private Optional<Position[][]> getPlayableField() {
        List<List<Map<String, Boolean>>> res = FieldConverter.fromJson(List.class, field);
        if (res == null) {
            return Optional.empty();
        }
        Position[][] field = new Position[size][size];
        for (int i = 0; i < res.size(); i++) {
            for (int j = 0; j < res.get(i).size(); j++) {
                Map<String, Boolean> vals = res.get(i).get(j);
                field[i][j] = new Position(vals.get("mined"), vals.get("open"), vals.get("flag"));
            }
        }
        return Optional.of(field);
    }

    public void open(int x, int y) {
        Position[][] sfield = getPlayableField().get();
        Position position = sfield[x][y];
        if (position.isOpen()) {
            return;
        }
        if (position.isMined()) {
            state = GameState.EXPLODED;
            endTime = new Date();
            return;
        }
        position.open();
        openNeighborhood(sfield, x, y);
        field = FieldConverter.toJson(sfield);
        if (!hasAvailablePositions(sfield)) {
            state = GameState.FINISHED;
            endTime = new Date();
        }
    }

    private boolean hasAvailablePositions(Position[][] sfield) {
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                Position pos = sfield[i][j];
                if (pos.isFlag() || !pos.isOpen()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void openIfNotMineOrFlagged(Position[][] sfield, int x, int y) {
        if (x < 0 || x >= size
                || y < 0 || y >= size
                || sfield[x][y].isMined()
                || sfield[x][y].isFlag()) {
            return;
        }
        sfield[x][y].open();
    }

    /**
     * .........
     * ..a.b.c..
     * ..d.X.e..
     * ..f.g.h..
     * .........
     *
     * @param sfield
     * @param x
     * @param y
     */
    private void openNeighborhood(Position[][] sfield, int x, int y) {
        // a
        openIfNotMineOrFlagged(sfield, x - 1, y - 1);
        // d
        openIfNotMineOrFlagged(sfield, x - 1, y);
        // f
        openIfNotMineOrFlagged(sfield, x - 1, y + 1);

        // c
        openIfNotMineOrFlagged(sfield, x + 1, y - 1);
        // e
        openIfNotMineOrFlagged(sfield, x + 1, y);
        // h
        openIfNotMineOrFlagged(sfield, x + 1, y + 1);

        // b
        openIfNotMineOrFlagged(sfield, x, y - 1);
        // g
        openIfNotMineOrFlagged(sfield, x, y + 1);
    }

    public void setFlag(Integer x, Integer y) {
        Position[][] sfield = getPlayableField().get();
        sfield[x][y].setFlag();
        field = FieldConverter.toJson(sfield);
    }

    public void setSize(Integer size) {
        this.size = size;
    }

        private final static String POSITION_DELIMITER = "|";

    public String getTextRepresentation() {
        StringBuffer buffer = new StringBuffer();
        for (Position[] row : getPlayableField().get()) {
            buffer.append(POSITION_DELIMITER +
                    Joiner.on(POSITION_DELIMITER)
                            .join(Arrays.stream(row)
                                    .map(Position::getValue)
                                    .collect(Collectors.toList())) +
                    POSITION_DELIMITER + "\n");
        }
        return buffer.toString();
    }
}
