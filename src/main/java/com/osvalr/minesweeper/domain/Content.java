package com.osvalr.minesweeper.domain;

public enum Content {
    NOTHING("."),
    MINE("*");

    private final String value;

    Content(String value ) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
