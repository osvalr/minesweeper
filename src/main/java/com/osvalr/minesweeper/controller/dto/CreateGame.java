package com.osvalr.minesweeper.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.osvalr.minesweeper.domain.GameSize;

public class CreateGame {
    private final GameSize gameSize;

    @JsonCreator
    public CreateGame(@JsonProperty("size") GameSize gameSize) {
        this.gameSize = gameSize;
    }

    public GameSize getGameSize() {
        return gameSize;
    }
}
