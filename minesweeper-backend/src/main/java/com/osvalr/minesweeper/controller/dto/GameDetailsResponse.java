package com.osvalr.minesweeper.controller.dto;

public class GameDetailsResponse {
    private Long gameId;
    private Long gameTime;
    private Long endTime;
    private String field;

    public GameDetailsResponse() {
    }

    public GameDetailsResponse(Long gameId,
                               Long gameTime,
                               Long endTime,
                               String field) {
        this.gameId = gameId;
        this.gameTime = gameTime;
        this.endTime = endTime;
        this.field = field;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getGameTime() {
        return gameTime;
    }

    public void setGameTime(Long gameTime) {
        this.gameTime = gameTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
