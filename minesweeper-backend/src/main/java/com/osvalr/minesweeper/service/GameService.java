package com.osvalr.minesweeper.service;

import com.osvalr.minesweeper.controller.dto.GameResponse;
import com.osvalr.minesweeper.domain.Game;
import com.osvalr.minesweeper.domain.GameSize;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface GameService {
    GameResponse create(@Nonnull GameSize gameSize);

    Optional<Game> getGameById(@Nonnull Long gameId);

    void flagPosition(@Nonnull Game game, int x, int y);

    void openPosition(@Nonnull Game game, int x, int y);
}
