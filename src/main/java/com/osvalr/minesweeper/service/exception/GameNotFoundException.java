package com.osvalr.minesweeper.service.exception;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

public class GameNotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE = "%s could not be found";

    public GameNotFoundException(@Nonnull String gameId) {
        super(String.format(ERROR_MESSAGE, requireNonNull(gameId)));
    }
}
