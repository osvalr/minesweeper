package com.osvalr.minesweeper.domain;

import java.io.Serializable;

public class Position implements Serializable {
    private boolean mined;
    private boolean open = false;
    private boolean flag = false;

    public Position() {
    }

    public Position(boolean hasMine) {
        this.mined = hasMine;
    }

    public Position(boolean mined, boolean open, boolean flag) {
        this.mined = mined;
        this.open = open;
        this.flag = flag;
    }

    public boolean isOpen() {
        return open;
    }

    public void open() {
        open = true;
    }

    public boolean isFlag() {
        return flag;
    }

    public void toggleFlag() {
        flag = !flag;
    }

    public boolean isMined() {
        return mined;
    }

    public static String getValue(Position position) {
        if (position.isFlag()) {
            return "?";
        }
        return position.isOpen() ? position.isMined() ? "*" : " " : "#";
    }
}
