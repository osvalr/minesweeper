package com.osvalr.minesweeper.domain;

import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Entity
public class Game extends BaseEntity {

    private Date startTime;
    private Date endTime;
    @Column(columnDefinition = "LONGVARCHAR")
    private String fieldStr;
    @Transient
    private Position[][] field;
    private int size;
    private double mines;
    private GameState state;

    public Game() {
    }

    public Game( int size, double mines) {
        this.fieldStr = FieldConverter.toJson(getNewField(size, mines));
        this.startTime = new Date();
        this.size = size;
        this.mines = mines;
        this.state = GameState.IN_PROGRESS;
    }

    private Position[][] getNewField(int size, double mines) {
        Position[][] field = new Position[size][size];
        for (int i = 0; i < size; ++i)
            for (int j = 0; j < size; ++j)
                field[i][j] = new Position(new Random().nextDouble() < mines);
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

    public String getFieldStr() {
        return fieldStr;
    }

    public void setFieldStr(String fieldStr) {
        this.fieldStr = fieldStr;
    }

    @PostLoad
    private void setField() {
        List<List<Map<String, Boolean>>> res = FieldConverter.fromJson(List.class, fieldStr);
        if (res == null) {
            field = null;
        }
        field = new Position[size][size];
        for (int i = 0; i < res.size(); i++) {
            for (int j = 0; j < res.get(i).size(); j++) {
                Map<String, Boolean> vals = res.get(i).get(j);
                field[i][j] = new Position(vals.get("mined"), vals.get("open"), vals.get("flag"));
            }
        }
    }

    public void open(int x, int y) {
        Position position = field[y][x];
        if (position.isOpen()) {
            return;
        }
        if (position.isMined()) {
            state = GameState.EXPLODED;
            endTime = new Date();
            return;
        }
        position.open();
        openNeighborhood(field, y, x);
        fieldStr = FieldConverter.toJson(field);
        if (!hasAvailablePositions()) {
            state = GameState.FINISHED;
            endTime = new Date();
        }
    }

    private boolean hasAvailablePositions() {
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                Position pos = field[i][j];
                if (pos.isFlag() || !pos.isOpen()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void openIfNotMineOrFlagged(int x, int y) {
        if (x < 0 || x >= size
                || y < 0 || y >= size
                || field[x][y].isMined()
                || field[x][y].isFlag()) {
            return;
        }
        field[x][y].open();
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
        openIfNotMineOrFlagged(x - 1, y - 1);
        // d
        openIfNotMineOrFlagged(x - 1, y);
        // f
        openIfNotMineOrFlagged(x - 1, y + 1);

        // c
        openIfNotMineOrFlagged(x + 1, y - 1);
        // e
        openIfNotMineOrFlagged(x + 1, y);
        // h
        openIfNotMineOrFlagged(x + 1, y + 1);

        // b
        openIfNotMineOrFlagged(x, y - 1);
        // g
        openIfNotMineOrFlagged(x, y + 1);
    }

    public void toggleFlag(Integer x, Integer y) {
        field[y][x].toggleFlag();
        fieldStr = FieldConverter.toJson(field);
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
