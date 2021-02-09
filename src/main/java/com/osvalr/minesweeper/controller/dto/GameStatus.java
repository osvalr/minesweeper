package com.osvalr.minesweeper.controller.dto;

public class GameStatus {
    private String gameId;
    private String gameTime;

    public GameStatus() {
    }

    public GameStatus(String gameId,
                      String gameTime) {
        this.gameId = gameId;
        this.gameTime = gameTime;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameTime() {
        return gameTime;
    }

    public void setGameTime(String gameTime) {
        this.gameTime = gameTime;
    }
}
