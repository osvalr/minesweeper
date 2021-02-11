package com.osvalr.minesweeper.controller;

import com.osvalr.minesweeper.controller.dto.CreateGameRequest;
import com.osvalr.minesweeper.controller.dto.GameDetailsResponse;
import com.osvalr.minesweeper.controller.dto.GameResponse;
import com.osvalr.minesweeper.controller.dto.PositionRequest;
import com.osvalr.minesweeper.domain.Game;
import com.osvalr.minesweeper.exception.GameNotCreatedException;
import com.osvalr.minesweeper.exception.GameNotFoundException;
import com.osvalr.minesweeper.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
public class GameController {
    private final GameService gameService;

    @Inject
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/games/")
    public ResponseEntity<GameResponse> createGame(@RequestBody CreateGameRequest createGame) {
        int size = createGame.getSize();
        if (size < 1 || size > 100) {
            throw new GameNotCreatedException(size + " isn't a valid value");
        }
        double mines = createGame.getMinesPercentage();
        if (mines < 0.10 || mines > 1.00) {
            throw new GameNotCreatedException(mines + " isn't a valid value");
        }
        GameResponse game = gameService.create(createGame.getSize(), createGame.getMinesPercentage());
        return ResponseEntity.ok(game);
    }

    @GetMapping("/games/")
    public ResponseEntity<List<GameDetailsResponse>> getAllGames() {
        Optional<List<Game>> listOptional = gameService.getAllGames();
        if (!listOptional.isPresent()) {
            throw new GameNotFoundException("");
        }
        List<Game> gameList = listOptional.get();
        return ResponseEntity.ok(gameList.stream().map(it -> new GameDetailsResponse(it.getId(),
                it.getStartTime() != null ? it.getStartTime().getTime() : null,
                it.getEndTime() != null ? it.getEndTime().getTime() : null,
                it.getFieldStr())).collect(Collectors.toList()));
    }

    @GetMapping("/games/{id}/details")
    public ResponseEntity<GameDetailsResponse> getGameDetails(@PathVariable("id") Long gameId) {
        Optional<Game> gameOptional = gameService.getGameById(gameId);
        if (!gameOptional.isPresent()) {
            throw new GameNotFoundException(gameId.toString());
        }
        Game game = gameOptional.get();
        return ResponseEntity.ok(new GameDetailsResponse(game.getId(),
                game.getStartTime() != null ? game.getStartTime().getTime() : null,
                game.getEndTime() != null ? game.getEndTime().getTime() : null,
                game.getFieldStr()));
    }

    @PostMapping("/games/{id}/flag")
    public ResponseEntity<?> flagPosition(@PathVariable("id") Long gameId,
                                          @RequestBody PositionRequest positionRequest) {
        Optional<Game> gameOptional = gameService.getGameById(gameId);
        if (!gameOptional.isPresent()) {
            throw new GameNotFoundException(gameId.toString());
        }
        Game game = gameOptional.get();
        gameService.flagPosition(game, positionRequest.getX(), positionRequest.getY());
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @PostMapping("/games/{id}/open")
    public ResponseEntity<?> openPosition(@PathVariable("id") Long gameId,
                                          @RequestBody PositionRequest positionRequest) {
        Optional<Game> gameOptional = gameService.getGameById(gameId);
        if (!gameOptional.isPresent()) {
            throw new GameNotFoundException(gameId.toString());
        }
        Game game = gameOptional.get();
        gameService.openPosition(game, positionRequest.getX(), positionRequest.getY());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
