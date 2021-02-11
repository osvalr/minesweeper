package com.osvalr.minesweeper.controller.dto;

public class GameResponse {
    private Long gameId;
    private Long gameTime;

    public GameResponse() {
    }

    public GameResponse(Long gameId,
                        Long gameTime) {
        this.gameId = gameId;
        this.gameTime = gameTime;
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
}
