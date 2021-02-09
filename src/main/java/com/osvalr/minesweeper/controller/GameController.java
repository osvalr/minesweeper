package com.osvalr.minesweeper.controller;

import com.osvalr.minesweeper.controller.dto.CreateGame;
import com.osvalr.minesweeper.controller.dto.GameStatus;
import com.osvalr.minesweeper.domain.Game;
import com.osvalr.minesweeper.service.GameService;
import com.osvalr.minesweeper.service.exception.GameNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/game/")
    public ResponseEntity<GameStatus> createGame(@RequestBody CreateGame createGame) {
        GameStatus game = gameService.create(createGame.getGameSize());
        return ResponseEntity.ok(game);
    }

    @GetMapping("/game/")
    public ResponseEntity<GameStatus> getGameDetails(@RequestParam("id") String gameId) {
        Optional<Game> gameOptional = gameService.getGameById(gameId);
        if (!gameOptional.isPresent()) {
            throw new GameNotFoundException(gameId);
        }
        Game game = gameOptional.get();
        return ResponseEntity.ok(new GameStatus(game.getGameId(), "", game.getStartTime().toString()));
    }
}
