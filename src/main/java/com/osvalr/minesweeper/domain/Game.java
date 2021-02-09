package com.osvalr.minesweeper.domain;

import org.apache.commons.lang3.RandomUtils;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.UUID;

public class Game {

    private String gameId;
    private Date startTime;
    private Position[][] field;
    private int size;
    private GameState state;

    public Game() {
    }

    public Game(@Nonnull GameSize gameSize) {
        this.gameId = UUID.randomUUID().toString();
        this.field = new Position[gameSize.getSize()][gameSize.getSize()];
        this.startTime = new Date();
        this.size = gameSize.getSize();
        this.state = GameState.IN_PROGRESS;
        initPositions();
    }

    private void initPositions() {
        for (int i = 0; i < this.size; ++i)
            for (int j = 0; j < this.size; ++j)
                this.field[i][j] = new Position(RandomUtils.nextDouble(0, 1) < 0.1);
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
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

    public Position[][] getField() {
        return field;
    }

    public void setField(Position[][] field) {
        this.field = field;
    }

    public void open(int x, int y) {
        Position position = field[x][y];
        if (position.isOpen()) {
            return;
        }
        if (position.isMined()) {
            // BOOM!
            state = GameState.EXPLODED;
            return;
        }
        position.open();
        openNeighborhood(x, y);
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
     * @param x
     * @param y
     */
    private void openNeighborhood(int x, int y) {
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

    public void setFlag(Integer x, Integer y) {
        field[x][y].setFlag();
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
