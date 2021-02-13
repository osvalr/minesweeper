package com.osvalr.minesweeper.controller;

import com.google.common.collect.Lists;
import com.osvalr.minesweeper.controller.dto.CreateGameRequest;
import com.osvalr.minesweeper.controller.dto.GameDetailsResponse;
import com.osvalr.minesweeper.controller.dto.GameResponse;
import com.osvalr.minesweeper.controller.dto.PositionRequest;
import com.osvalr.minesweeper.domain.Game;
import com.osvalr.minesweeper.domain.User;
import com.osvalr.minesweeper.exception.GameNotCreatedException;
import com.osvalr.minesweeper.exception.GameNotFoundException;
import com.osvalr.minesweeper.exception.SessionTokenNotValid;
import com.osvalr.minesweeper.repository.UserRepository;
import com.osvalr.minesweeper.service.GameService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
public class GameController {
    private final GameService gameService;
    private final UserRepository userRepository;

    @Inject
    public GameController(GameService gameService,
                          UserRepository userRepository) {
        this.gameService = gameService;
        this.userRepository = userRepository;
    }

    @ApiOperation(
            value = "Create a game based on size and mines threshold"
    )
    @PostMapping("/games/")
    public ResponseEntity<GameResponse> createGame(
            @ApiParam(value = "Mines threshold and dimension for the Y,X to be based on when creating a game",
                    example = "{ 'size': 5, 'mines': 0.198 }")
            @RequestBody CreateGameRequest createGame,
            @ApiParam(value = "Session token for the current user")
            @RequestHeader("Authorization") String auth) {
        Optional<User> userOptional = userRepository.findByActiveToken(auth);
        if (!userOptional.isPresent()) {
            throw new SessionTokenNotValid();
        }
        int cols = createGame.getCols();
        if (cols < 1 || cols > 100) {
            throw new GameNotCreatedException(cols + " isn't a valid value");
        }
        int rows = createGame.getRows();
        if (rows < 1 || rows > 100) {
            throw new GameNotCreatedException(rows + " isn't a valid value");
        }
        double mines = createGame.getMinesPercentage();
        if (mines < 0.10 || mines > 1.00) {
            throw new GameNotCreatedException(mines + " isn't a valid value");
        }
        GameResponse game = gameService.create(userOptional.get(), rows, cols, createGame.getMinesPercentage());
        return ResponseEntity.ok(game);
    }

    @ApiOperation(
            value = "Get all games for a user",
            notes = "Login is required"
    )
    @GetMapping("/games/")
    public ResponseEntity<List<GameDetailsResponse>> getAllGames(
            @ApiParam(
                    value = "Session token for the current user"
            )
            @RequestHeader("Authorization") String authorization) {
        Optional<User> userOptional = userRepository.findByActiveToken(authorization);
        if (!userOptional.isPresent()) {
            throw new SessionTokenNotValid();
        }
        Optional<List<Game>> optionalGameList = gameService.getAllOpenGamesByUserId(userOptional.get().getId());
        return ResponseEntity.ok(optionalGameList.
                map(games -> games.stream()
                        .map(it -> new GameDetailsResponse(it.getId(),
                                it.getStartTime() != null ? it.getStartTime().getTime() : null,
                                it.getEndTime() != null ? it.getEndTime().getTime() : null,
                                it.getFieldStr(),
                                it.getState().ordinal()))
                        .collect(Collectors.toList()))
                .orElseGet(Lists::newArrayList));
    }

    @ApiOperation(
            value = "Get details for a game such as field, mines, end time, start time"
    )
    @GetMapping("/games/{id}/details")
    public ResponseEntity<GameDetailsResponse> getGameDetails(
            @ApiParam(
                    value = "Id of the game to get details for"
            )
            @PathVariable("id") Long gameId) {
        Optional<Game> gameOptional = gameService.getGameById(gameId);
        if (!gameOptional.isPresent()) {
            throw new GameNotFoundException(gameId.toString());
        }
        Game game = gameOptional.get();
        return ResponseEntity.ok(new GameDetailsResponse(game.getId(),
                game.getStartTime() != null ? game.getStartTime().getTime() : null,
                game.getEndTime() != null ? game.getEndTime().getTime() : null,
                game.getFieldStr(),
                game.getState().ordinal()));
    }

    @ApiOperation(
            value = "Set a flag a position in the game field"
    )
    @PostMapping("/games/{id}/flags")
    public ResponseEntity<?> flagPosition(
            @ApiParam(value = "Id of the game the flag will be placed on")
            @PathVariable("id") Long gameId,
            @ApiParam(value = "Y,X coordinates where the flag will be placed",
                    example = "{'x': 1, 'y': 1}",
                    required = true)
            @RequestBody PositionRequest positionRequest) {
        Optional<Game> gameOptional = gameService.getGameById(gameId);
        if (!gameOptional.isPresent()) {
            throw new GameNotFoundException(gameId.toString());
        }
        Game game = gameOptional.get();
        gameService.flagPosition(game, positionRequest.getX(), positionRequest.getY());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(
            value = "Open a position in the field of the game"
    )
    @PostMapping("/games/{id}/positions")
    public ResponseEntity<?> openPosition(
            @ApiParam(value = "Id of the game the position will be opened")
            @PathVariable("id") Long gameId,
            @ApiParam(value = "Y,X coordinates indicating the position to be opened",
                    example = "{'x': 1, 'y': 1}",
                    required = true
            )
            @RequestBody PositionRequest positionRequest) {
        Optional<Game> gameOptional = gameService.getGameById(gameId);
        if (!gameOptional.isPresent()) {
            throw new GameNotFoundException(gameId.toString());
        }
        Game game = gameOptional.get();
        gameService.openPosition(game, positionRequest.getY(), positionRequest.getX());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
