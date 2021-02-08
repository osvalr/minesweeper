package com.osvalr.minesweeper.service;

import com.osvalr.minesweeper.controller.dto.GameStatus;
import com.osvalr.minesweeper.domain.Game;
import com.osvalr.minesweeper.domain.GameSize;
import com.osvalr.minesweeper.repository.GameRepository;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public GameStatus create(GameSize gameSize) {
        Game game = new Game(gameSize);
        game = gameRepository.save(game);
        return new GameStatus(game.getGameId(), "", game.getStartTime().toString());
    }
}
