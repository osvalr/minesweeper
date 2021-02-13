package com.osvalr.minesweeper.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateGameRequest {
    private final int rows;
    private final int cols;
    private final double minesPercentage;

    @JsonCreator
    public CreateGameRequest(@JsonProperty("rows") int rows,
                             @JsonProperty("cols") int cols,
                             @JsonProperty("mines") double minesPercentage) {
        this.rows = rows;
        this.cols = cols;
        this.minesPercentage = minesPercentage;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public double getMinesPercentage() {
        return minesPercentage;
    }
}
