package com.osvalr.minesweeper.domain;

public enum GameSize {
    FIVE_BY_FIVE(5),
    TEN_BY_TEN(10);

    private final int size;

    GameSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
