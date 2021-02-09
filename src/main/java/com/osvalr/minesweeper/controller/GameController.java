package com.osvalr.minesweeper.controller;

import com.osvalr.minesweeper.controller.dto.CreateGame;
import com.osvalr.minesweeper.controller.dto.GameStatus;
import com.osvalr.minesweeper.controller.dto.PositionRequest;
import com.osvalr.minesweeper.domain.Game;
import com.osvalr.minesweeper.exception.GameNotFoundException;
import com.osvalr.minesweeper.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Optional;

@RestController
public class GameController {
    private final GameService gameService;

    @Inject
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/games/")
    public ResponseEntity<GameStatus> createGame(@RequestBody CreateGame createGame) {
        GameStatus game = gameService.create(createGame.getGameSize());
        return ResponseEntity.ok(game);
    }

    @GetMapping("/games/")
    public ResponseEntity<GameStatus> getGameDetails(@RequestParam("id") String gameId) {
        Optional<Game> gameOptional = gameService.getGameById(gameId);
        if (!gameOptional.isPresent()) {
            throw new GameNotFoundException(gameId);
        }
        Game game = gameOptional.get();
        return ResponseEntity.ok(new GameStatus(game.getGameId(),  game.getStartTime().toString()));
    }

    @PatchMapping("/games/{id}/flag")
    public ResponseEntity<GameStatus> flagPosition(@PathVariable("id") String gameId,
                                                   @RequestBody PositionRequest positionRequest) {
        Optional<Game> gameOptional = gameService.getGameById(gameId);
        if (!gameOptional.isPresent()) {
            throw new GameNotFoundException(gameId);
        }
        Game game = gameOptional.get();
        gameService.flagPosition(game, positionRequest.getX(), positionRequest.getY());
        return ResponseEntity.ok(new GameStatus(game.getGameId(), game.getStartTime().toString()));
    }


    @PatchMapping("/games/{id}/open")
    public ResponseEntity<GameStatus> openPosition(@PathVariable("id") String gameId,
                                                   @RequestBody PositionRequest positionRequest) {
        Optional<Game> gameOptional = gameService.getGameById(gameId);
        if (!gameOptional.isPresent()) {
            throw new GameNotFoundException(gameId);
        }
        Game game = gameOptional.get();
        gameService.openPosition(game, positionRequest.getX(), positionRequest.getY());
        return ResponseEntity.ok(new GameStatus(game.getGameId(),  game.getStartTime().toString()));
    }
}
