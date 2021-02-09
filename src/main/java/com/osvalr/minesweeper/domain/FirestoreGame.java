package com.osvalr.minesweeper.domain;

import java.util.Date;

public class FirestoreGame {

    private String gameId;
    private Date startTime;
    private String field;

    public FirestoreGame(String gameId, Date startTime, String field) {
        this.gameId = gameId;
        this.startTime = startTime;
        this.field = field;
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
}
