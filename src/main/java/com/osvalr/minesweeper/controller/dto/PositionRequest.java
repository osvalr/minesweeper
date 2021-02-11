package com.osvalr.minesweeper.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PositionRequest {
    private final int x;
    private final int y;

    @JsonCreator
    public PositionRequest(@JsonProperty("x") int x,
                           @JsonProperty("y") int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
