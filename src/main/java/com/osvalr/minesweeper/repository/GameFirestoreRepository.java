package com.osvalr.minesweeper.repository;

import com.osvalr.minesweeper.domain.Game;

import java.util.Optional;

public interface GameFirestoreRepository {
    void save(Game game);

    Optional<Game> getByGameId(String gameId);
}
