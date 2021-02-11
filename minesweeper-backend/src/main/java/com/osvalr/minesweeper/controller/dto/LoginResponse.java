package com.osvalr.minesweeper.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse {
    private final String token;

    @JsonCreator
    public LoginResponse(@JsonProperty("token") String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
