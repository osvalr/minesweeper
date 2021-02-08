package com.osvalr.minesweeper.service;

import com.osvalr.minesweeper.controller.dto.GameStatus;
import com.osvalr.minesweeper.domain.GameSize;

public interface GameService {
    GameStatus create(GameSize gameSize);
}
