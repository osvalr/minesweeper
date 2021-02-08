package com.osvalr.minesweeper.controller.dto;

public class GameStatus {
    private String gameId;
    private String strRep;
    private String gameTime;

    public GameStatus() {
    }

    public GameStatus(String gameId,
                      String strRep,
                      String gameTime) {
        this.gameId = gameId;
        this.strRep = strRep;
        this.gameTime = gameTime;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getStrRep() {
        return strRep;
    }

    public void setStrRep(String strRep) {
        this.strRep = strRep;
    }

    public String getGameTime() {
        return gameTime;
    }

    public void setGameTime(String gameTime) {
        this.gameTime = gameTime;
    }
}
