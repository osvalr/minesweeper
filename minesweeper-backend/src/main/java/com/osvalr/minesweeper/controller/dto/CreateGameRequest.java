package com.osvalr.minesweeper.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.osvalr.minesweeper.domain.GameSize;

public class CreateGameRequest {
    private final int size;
    private final double minesPercentage;

    @JsonCreator
    public CreateGameRequest(@JsonProperty("size") int size,
                             @JsonProperty("mines") double minesPercentage) {
        this.size = size;
        this.minesPercentage = minesPercentage;
    }

    public int getSize() {
        return size;
    }

    public double getMinesPercentage() {
        return minesPercentage;
    }
}
