package com.osvalr.minesweeper.domain;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class Game {

    private String gameId;
    private Date startTime;
    private GameSize gameSize;

    public Game() {
    }

    public Game(@Nonnull GameSize gameSize) {
        this.gameId = UUID.randomUUID().toString();
        this.gameSize = requireNonNull(gameSize);
        this.startTime = new Date();
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

    public GameSize getGameSize() {
        return gameSize;
    }

    public void setGameSize(GameSize gameSize) {
        this.gameSize = gameSize;
    }
}
