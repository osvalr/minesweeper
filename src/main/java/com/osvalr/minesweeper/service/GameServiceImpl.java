package com.osvalr.minesweeper.service;

import com.osvalr.minesweeper.controller.dto.GameStatus;
import com.osvalr.minesweeper.domain.Game;
import com.osvalr.minesweeper.domain.GameSize;
import com.osvalr.minesweeper.exception.PositionOutOfBounds;
import com.osvalr.minesweeper.repository.GameFirestoreRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.function.BiConsumer;

@Service
public class GameServiceImpl implements GameService {
    private final GameFirestoreRepository gameFirestoreRepository;

    public GameServiceImpl(GameFirestoreRepository gameFirestoreRepository) {
        this.gameFirestoreRepository = gameFirestoreRepository;
    }

    @Override
    public GameStatus create(@Nonnull GameSize gameSize) {
        Game game = new Game(gameSize);
        gameFirestoreRepository.save(game);
        return new GameStatus(game.getGameId(), game.getStartTime().toString());
    }

    @Override
    public Optional<Game> getGameById(@Nonnull String gameId) {
        return gameFirestoreRepository.getByGameId(gameId);
    }

    private void validateBounds(int expectedSize, int x, int y) {
        if (x < 1 || x > expectedSize
                || y < 1 || y > expectedSize) {
            throw new PositionOutOfBounds("X or Y is out of bounds");
        }
    }

    private void runAndSave(BiConsumer<Integer, Integer> op, int x, int y, Game game) {
        op.accept(x, y);
        gameFirestoreRepository.save(game);
    }

    @Override
    public void flagPosition(@Nonnull Game game, int x, int y) {
        validateBounds(game.getSize(), x, y);
        runAndSave(game::setFlag, x, y, game);
    }

    @Override
    public void openPosition(@Nonnull Game game, int x, int y) {
        validateBounds(game.getSize(), x, y);
        runAndSave(game::open, x - 1, y - 1, game);
    }
}
