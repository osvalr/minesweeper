package com.osvalr.minesweeper.controller.dto;

public class GameStatus {
    private Long gameId;
    private String gameTime;
    private String text;

    public GameStatus() {
    }

    public GameStatus(Long gameId,
                      String gameTime) {
        this.gameId = gameId;
        this.gameTime = gameTime;
    }

    public GameStatus(Long gameId,
                      String text,
                      String gameTime) {
        this.gameId = gameId;
        this.text = text;
        this.gameTime = gameTime;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getGameTime() {
        return gameTime;
    }

    public void setGameTime(String gameTime) {
        this.gameTime = gameTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
