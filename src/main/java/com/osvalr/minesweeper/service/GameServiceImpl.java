package com.osvalr.minesweeper.service;

import com.osvalr.minesweeper.controller.dto.GameStatus;
import com.osvalr.minesweeper.domain.Game;
import com.osvalr.minesweeper.domain.GameSize;
import com.osvalr.minesweeper.repository.GameFirestoreRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {
    private final GameFirestoreRepository gameFirestoreRepository;

    public GameServiceImpl(GameFirestoreRepository gameFirestoreRepository) {
        this.gameFirestoreRepository = gameFirestoreRepository;
    }

    @Override
    public GameStatus create(GameSize gameSize) {
        Game game = new Game(gameSize);
        gameFirestoreRepository.save(game);
        return new GameStatus(game.getGameId(), "", game.getStartTime().toString());
    }

    @Override
    public Optional<Game> getGameById(String gameId) {
        return gameFirestoreRepository.getByGameId(gameId);
    }
}
