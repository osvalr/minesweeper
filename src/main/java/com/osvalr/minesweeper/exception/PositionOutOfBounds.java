package com.osvalr.minesweeper.exception;

public class PositionOutOfBounds extends RuntimeException {
    public PositionOutOfBounds(String message) {
        super(message);
    }
}
