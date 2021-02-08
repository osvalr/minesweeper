package com.osvalr.minesweeper.domain;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Entity
public class Game extends BaseEntity {

    private final String gameId;
    private final Date startTime;

    @Enumerated(EnumType.ORDINAL)
    private final GameSize gameSize;

    public Game(@Nonnull GameSize gameSize) {
        this.gameId = UUID.randomUUID().toString();
        this.gameSize = requireNonNull(gameSize);
        this.startTime = new Date();
    }

    public String getGameId() {
        return gameId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public GameSize getGameSize() {
        return gameSize;
    }
}
