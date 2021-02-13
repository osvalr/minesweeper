package com.osvalr.minesweeper.domain;

import com.google.common.collect.Lists;
import com.osvalr.minesweeper.exception.GameExplodedException;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Random;

import static org.springframework.data.util.Pair.of;

public class MineField {
    /**
     * @param size Size of the field
     * @param minesThreshold Mine threshold
     * @return Mine field
     */
    public static MineField fromSize(int size, double minesThreshold) {
        MineField mineField = new MineField(new Position[size][size], size);
        List<Pair<Integer, Integer>> minesList = Lists.newArrayList();
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                boolean hasMine = new Random().nextDouble() < minesThreshold;
                if (hasMine) {
                    minesList.add(of(i, j));
                }
                mineField.field[i][j] = new Position(hasMine);
            }
        }
        for (Pair<Integer, Integer> pos : minesList) {
            setHints(mineField.field, size, pos);
        }
        return mineField;
    }

    private final Position[][] field;
    private final int size;

    /**
     * @param field Mine field
     * @param size Size of the field
     */
    public MineField(Position[][] field, int size) {
        this.field = field;
        this.size = size;
    }

    /**
     * Increment hints around pos
     * @param field 2-D array representation of the field
     * @param size size of the board
     * @param pos (Row,Col) of the position where adjacent cells will be incremented
     */
    private static void setHints(Position[][] field, int size, Pair<Integer, Integer> pos) {
        int row = pos.getFirst();
        int col = pos.getSecond();
        if (row - 1 >= 0) {
            if (col - 1 >= 0) {
                field[row - 1][col - 1].incrementHint();
            }
            field[row - 1][col].incrementHint();
            if (col + 1 < size) {
                field[row - 1][col + 1].incrementHint();
            }
        }
        if (col - 1 >= 0) {
            field[row][col - 1].incrementHint();
        }
        if (col + 1 < size) {
            field[row][col + 1].incrementHint();
        }
        if (row + 1 < size) {
            if (col - 1 >= 0) {
                field[row + 1][col - 1].incrementHint();
            }
            field[row + 1][col].incrementHint();
            if (col + 1 < size) {
                field[row + 1][col + 1].incrementHint();
            }
        }
    }

    /**
     * Set position in (row,col)
     * @param row Row of the position
     * @param col Column of position
     * @param position content in (row,col)
     */
    public void set(int row, int col, Position position) {
        field[row][col] = position;
    }

    /**
     * Open position (row,col)
     * @exception GameExplodedException if a mined was opened
     * @param row Row of the position
     * @param col Column of position
     */
    public void open(int row, int col) throws GameExplodedException {
        Position position = field[row][col];
        if (position.isOpen()) {
            return;
        }
        if (position.isMined()) {
            throw new GameExplodedException();
        }
        position.open();
        openNeighborhood(row, col);
    }

    /**
     * Check the fields for positions not open and not mined
     * @return True if there is at least one position available, False otherwise
     */
    public boolean hasAvailablePositions() {
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (!field[i][j].isMined() && !field[i][j].isOpen()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Open position within boundaries of the field when either there isn't a mine nor a flag in there
     * @param row Row of the position
     * @param col Column of position
     */
    private void openIfNotMineOrFlagged(int row, int col) {
        if (row < 0 || row >= size
                || col < 0 || col >= size
                || field[row][col].isMined()
                || field[row][col].isFlag()) {
            return;
        }
        field[row][col].open();
    }

    /**
     * Open adjacent cells around the position (row,col)
     * @param row Row of the position
     * @param col Column of position
     */
    private void openNeighborhood(int row, int col) {
        openIfNotMineOrFlagged(row - 1, col - 1);
        openIfNotMineOrFlagged(row - 1, col);
        openIfNotMineOrFlagged(row - 1, col + 1);
        openIfNotMineOrFlagged(row + 1, col - 1);
        openIfNotMineOrFlagged(row + 1, col);
        openIfNotMineOrFlagged(row + 1, col + 1);
        openIfNotMineOrFlagged(row, col - 1);
        openIfNotMineOrFlagged(row, col + 1);
    }

    public void toggleFlag(int row, int col) {
        field[col][row].toggleFlag();
    }

    public Position[][] getField() {
        return field;
    }
}
