package com.osvalr.minesweeper.controller;

import com.osvalr.minesweeper.controller.dto.CreateGame;
import com.osvalr.minesweeper.controller.dto.GameStatus;
import com.osvalr.minesweeper.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

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
}
