package com.osvalr.minesweeper.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ErrorControllerAdvice {

    @ExceptionHandler(GameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleGameNotFoundException(GameNotFoundException exception, WebRequest request) {
        return exception.getMessage();
    }

    @ExceptionHandler(PositionOutOfBounds.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlePositionOutOfBounds(PositionOutOfBounds exception, WebRequest request) {
        return exception.getMessage();
    }

    @ExceptionHandler(GameFinishedException.class)
    @ResponseStatus(HttpStatus.FOUND)
    public String handleGameFinished(GameFinishedException exception, WebRequest request) {
        return "";
    }

    @ExceptionHandler(GameExplodedException.class)
    @ResponseStatus(HttpStatus.GONE)
    public String handleGameExplodedException(GameExplodedException exception, WebRequest request) {
        return "";
    }


    @ExceptionHandler(GameNotCreatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleGameNotCreatedException(GameNotCreatedException exception, WebRequest request) {
        return exception.getMessage();
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.FOUND)
    public String handleUserAlreadyExistsException(UserAlreadyExistsException exception, WebRequest request) {
        return "";
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleInvalidCredentialsException(InvalidCredentialsException exception, WebRequest request) {
        return "";
    }
}
