package com.osvalr.minesweeper.domain;


import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class GameTest {
    @Test
    public void testNewGame() {
        Game game = new Game(5, 0.3);
        assertEquals(5, game.getSize());
        assertNull(game.getEndTime());

        List<List<Map<String, Object>>> field = FieldConverter.fromJson(List.class, game.getFieldStr());
        List<Pair<Integer, Integer>> mineList = Lists.newArrayList();
        assertNotNull(field);
        int y = 0;
        Position[][] newField = new Position[game.getSize()][game.getSize()];
        for (List<Map<String, Object>> rows : field) {
            int x = 0;
            for (Map<String, Object> pos : rows) {
                Position position = new Position(
                        Boolean.parseBoolean(pos.get("mined").toString()),
                        Boolean.parseBoolean(pos.get("open").toString()),
                        Boolean.parseBoolean(pos.get("flag").toString()),
                        Integer.parseInt(pos.get("hint").toString()));
                if (position.isMined()) {
                    mineList.add(Pair.of(y, x));
                }
                newField[y][x] = position;
                ++x;
            }
            ++y;
        }
        print(newField, "Just created from tests");
        for (Pair<Integer, Integer> pos : mineList) {
            int row = pos.getFirst();
            int col = pos.getSecond();

            if (row - 1 >= 0) {
                if (col - 1 >= 0) {
                    newField[row - 1][col - 1] = decrementHint(newField[row - 1][col - 1]);
                }
                newField[row - 1][col] = decrementHint(newField[row - 1][col]);
                if (col + 1 < game.getSize()) {
                    newField[row - 1][col + 1] = decrementHint(newField[row - 1][col + 1]);
                }
            }

            if (col - 1 >= 0) {
                newField[row][col - 1] = decrementHint(newField[row][col - 1]);
            }
            if (col + 1 < game.getSize()) {
                newField[row][col + 1] = decrementHint(newField[row][col + 1]);
            }

            if (row + 1 < game.getSize()) {
                if (col - 1 >= 0) {
                    newField[row + 1][col - 1] = decrementHint(newField[row + 1][col - 1]);
                }
                newField[row + 1][col] = decrementHint(newField[row + 1][col]);
                if (col + 1 < game.getSize()) {
                    newField[row + 1][col + 1] = decrementHint(newField[row + 1][col + 1]);
                }
            }
            print(newField, String.format("Decrementing hints for: %d,%d", row, col));
        }
        print(newField, "After all decrements");
        for (Pair<Integer, Integer> pos : mineList) {
            int row = pos.getFirst();
            int col = pos.getSecond();

            if (row - 1 >= 0) {
                if (col - 1 >= 0) {
                    assertEquals(0, newField[row - 1][col - 1].getHint());
                }
                assertEquals(0, newField[row - 1][col].getHint());
                if (col + 1 < game.getSize()) {
                    assertEquals(0, newField[row - 1][col + 1].getHint());
                }
            }
            if (col - 1 >= 0) {
                assertEquals(0, newField[row][col - 1].getHint());
            }
            if (col + 1 < game.getSize()) {
                assertEquals(0, newField[row][col + 1].getHint());
            }
            if (row + 1 < game.getSize()) {
                if (col - 1 >= 0) {
                    assertEquals(0, newField[row + 1][col - 1].getHint());
                }
                assertEquals(0, newField[row + 1][col].getHint());
                if (col + 1 < game.getSize()) {
                    assertEquals(0, newField[row + 1][col + 1].getHint());
                }
            }
        }
    }

    private void print(Position[][] field, String format) {
        System.out.println("State: " + format);
        for (int i = 0; i < field[0].length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                System.out.print(String.format("(%s%3d)", field[i][j].isMined() ? "*," : "  ", field[i][j].getHint()));
            }
            System.out.println("");
        }
        System.out.println("");
    }

    private Position decrementHint(Position pos) {
        return new Position(
                pos.isMined(),
                pos.isOpen(),
                pos.isFlag(),
                pos.getHint() - 1
        );
    }
}