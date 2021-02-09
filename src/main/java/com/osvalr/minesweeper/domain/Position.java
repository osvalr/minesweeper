package com.osvalr.minesweeper.domain;

public class Position {
    private Content content;
    private boolean open = false;
    private boolean flag = false;

    public Position(boolean hasMine) {
        content = hasMine? Content.MINE : Content.NOTHING;
    }

    public void toggleFlag() {
        flag = !flag;
    }

    public boolean isOpen() {
        return open;
    }

    public void open() {
        open = true;
    }

    public boolean isAMine() {
        return content == Content.MINE;
    }

    public boolean isFlagged() {
        return flag;
    }

    public String getValue() {
        return content.getValue();
    }
}
