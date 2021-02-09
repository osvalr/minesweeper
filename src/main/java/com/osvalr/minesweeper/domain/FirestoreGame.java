package com.osvalr.minesweeper.domain;

import java.util.Date;

public class FirestoreGame {

    private String gameId;
    private Date startTime;
    private String field;
    private int size;
    private GameState state;

    public FirestoreGame(String gameId, Date startTime, String field, int size, GameState state) {
        this.gameId = gameId;
        this.startTime = startTime;
        this.field = field;
        this.size = size;
        this.state = state;
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

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }
}
