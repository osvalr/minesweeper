package com.osvalr.minesweeper.service;

import com.osvalr.minesweeper.controller.dto.GameResponse;
import com.osvalr.minesweeper.domain.Game;
import com.osvalr.minesweeper.domain.User;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface GameService {
    GameResponse create(User user, int rows, int cols, double mines);

    Optional<Game> getGameById(@Nonnull Long gameId);

    void flagPosition(@Nonnull Game game, int row, int col);

    void openPosition(@Nonnull Game game, int row, int col);

    Optional<List<Game>> getAllOpenGamesByUserId(Long id);
}
