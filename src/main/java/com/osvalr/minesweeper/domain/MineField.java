package com.osvalr.minesweeper.domain;

import com.google.common.collect.Lists;
import com.osvalr.minesweeper.exception.GameExplodedException;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Random;

import static org.springframework.data.util.Pair.of;

public class MineField {
    /**
     *
     * @param rows Number of rows
     * @param cols Number of columns
     * @param minesThreshold Mine threshold
     * @return Mine field
     */
    public static MineField fromSize(int rows, int cols, double minesThreshold) {
        MineField mineField = new MineField( rows,cols);
        List<Pair<Integer, Integer>> minesList = Lists.newArrayList();
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                boolean hasMine = new Random().nextDouble() < minesThreshold;
                if (hasMine) {
                    minesList.add(of(i, j));
                }
                mineField.field[i][j] = new Position(hasMine);
            }
        }
        for (Pair<Integer, Integer> pos : minesList) {
            setHints(mineField.field, rows, cols, pos);
        }
        return mineField;
    }

    private final Position[][] field;
    private final int rows;
    private final int cols;

    /**
     * @param rows Number of rows
     * @param cols Number of cols
     */
    public MineField(int rows ,int cols) {
        this.field = new Position[rows][cols];
        this.rows = rows;
        this.cols = cols;
    }

    /**
     * Increment hints around pos
     * @param field 2-D array representation of the field
     * @param rows Number of rows
     * @param cols Number of cols
     * @param pos (Row,Col) of the position where adjacent cells will be incremented
     */
    private static void setHints(Position[][] field, int rows, int cols, Pair<Integer, Integer> pos) {
        int row = pos.getFirst();
        int col = pos.getSecond();
        if (row - 1 >= 0) {
            if (col - 1 >= 0) {
                field[row - 1][col - 1].incrementHint();
            }
            field[row - 1][col].incrementHint();
            if (col + 1 < cols) {
                field[row - 1][col + 1].incrementHint();
            }
        }
        if (col - 1 >= 0) {
            field[row][col - 1].incrementHint();
        }
        if (col + 1 < cols) {
            field[row][col + 1].incrementHint();
        }
        if (row + 1 < rows) {
            if (col - 1 >= 0) {
                field[row + 1][col - 1].incrementHint();
            }
            field[row + 1][col].incrementHint();
            if (col + 1 < cols) {
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
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
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
        if (row < 0 || row >= rows
                || col < 0 || col >= cols
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
