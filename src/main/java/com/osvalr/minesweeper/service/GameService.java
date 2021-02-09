package com.osvalr.minesweeper.service;

import com.osvalr.minesweeper.controller.dto.GameStatus;
import com.osvalr.minesweeper.domain.Game;
import com.osvalr.minesweeper.domain.GameSize;

import java.util.Optional;

public interface GameService {
    GameStatus create(GameSize gameSize);

    Optional<Game> getGameById(String gameId);
}
