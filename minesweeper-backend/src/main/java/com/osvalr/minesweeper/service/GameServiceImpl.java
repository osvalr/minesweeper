package com.osvalr.minesweeper.service;

import com.osvalr.minesweeper.controller.dto.GameResponse;
import com.osvalr.minesweeper.domain.Game;
import com.osvalr.minesweeper.domain.GameState;
import com.osvalr.minesweeper.domain.User;
import com.osvalr.minesweeper.exception.GameExplodedException;
import com.osvalr.minesweeper.exception.GameFinishedException;
import com.osvalr.minesweeper.exception.PositionOutOfBounds;
import com.osvalr.minesweeper.repository.GameRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;

    @Inject
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public GameResponse create(User user, int size, double mines) {
        Game game = new Game(size, mines);
        game.setUser(user);
        gameRepository.save(game);
        return new GameResponse(game.getId(), game.getStartTime().getTime());
    }

    @Override
    public Optional<Game> getGameById(@Nonnull Long gameId) {
        return gameRepository.findById(gameId);
    }

    private void validateBounds(int expectedSize, int x, int y) {
        if (x < 1 || x > expectedSize
                || y < 1 || y > expectedSize) {
            throw new PositionOutOfBounds("X or Y is out of bounds");
        }
    }

    private void runAndSave(BiConsumer<Integer, Integer> op, int x, int y, Game game) {
        validateBounds(game.getSize(), x, y);
        op.accept(x - 1, y - 1);
        gameRepository.save(game);
    }

    @Override
    public void flagPosition(@Nonnull Game game, int x, int y) {
        runAndSave(game::toggleFlag, x, y, game);
    }

    @Override
    public void openPosition(@Nonnull Game game, int x, int y) {
        runAndSave(game::open, x, y, game);
        if (game.getState() == GameState.EXPLODED) {
            throw new GameExplodedException();
        }
        if (game.getState() == GameState.FINISHED) {
            throw new GameFinishedException();
        }
    }

    @Override
    public Optional<List<Game>> getAllGames() {
        return Optional.of(gameRepository.findAll());
    }
}
