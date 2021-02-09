package com.osvalr.minesweeper.domain;

import org.apache.commons.lang3.RandomUtils;

public class Field {
    private Position[][] field;
    private GameSize size;
    private FieldState fieldState;

    public Field(GameSize gameSize) {
        this.size = gameSize;
        this.field = new Position[gameSize.getSize()][gameSize.getSize()];
        this.fieldState = FieldState.IN_PROGRESS;
        initPositions();
    }

    private void initPositions() {
        for (int i = 0; i < this.size.getSize(); ++i)
            for (int j = 0; j < this.size.getSize(); ++j)
                this.field[i][j] = new Position(RandomUtils.nextDouble(0, 1) < 0.1);
    }

    public Position[][] getField() {
        return field;
    }

    public void setField(Position[][] field) {
        this.field = field;
    }

    public GameSize getSize() {
        return size;
    }

    public void setSize(GameSize size) {
        this.size = size;
    }

    public FieldState getFieldState() {
        return fieldState;
    }

    public void setFieldState(FieldState fieldState) {
        this.fieldState = fieldState;
    }

    public void open(int x, int y) {
        Position position = field[x][y];
        if (position.isOpen()) {
            // already open
            return;
        }
        if (position.isAMine()) {
            // BOOM!
            fieldState = FieldState.EXPLODED;
            return;
        }
        position.open();
        openNeighborhood(x, y);
    }

    private void openIfNotMineOrFlagged(int x, int y) {
        if (x < 0 || x >= size.getSize()
                || y < 0 || y >= size.getSize()
                || field[x][y].isAMine()
                || field[x][y].isFlagged()) {
            return;
        }
        field[x][y].open();
    }

    /**
     * .........
     * ..a.b.c..
     * ..d.X.e..
     * ..f.g.h..
     * .........
     *
     * @param x
     * @param y
     */
    private void openNeighborhood(int x, int y) {
        // a
        openIfNotMineOrFlagged(x - 1, y - 1);
        // d
        openIfNotMineOrFlagged(x - 1, y);
        // f
        openIfNotMineOrFlagged(x - 1, y + 1);

        // c
        openIfNotMineOrFlagged(x + 1, y - 1);
        // e
        openIfNotMineOrFlagged(x + 1, y);
        // h
        openIfNotMineOrFlagged(x + 1, y + 1);

        // b
        openIfNotMineOrFlagged(x, y - 1);
        // g
        openIfNotMineOrFlagged(x, y + 1);
    }

    public void init() {

    }

//    private final static String POSITION_DELIMITER = "|";

//    public String getTextRepresentation() {
//        StringBuffer buffer = new StringBuffer();
//        for (Position[] row : field) {
//            buffer.append(POSITION_DELIMITER +
//                    Joiner.on(POSITION_DELIMITER)
//                            .join(Arrays.stream(row)
//                                    .map(Position::getValue)
//                                    .collect(Collectors.toList())) +
//                    POSITION_DELIMITER + "\n");
//        }
//        return buffer.toString();
//    }
}
