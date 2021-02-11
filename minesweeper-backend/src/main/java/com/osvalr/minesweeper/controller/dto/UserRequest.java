package com.osvalr.minesweeper.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRequest {
    private final String user;
    private final String password;

    @JsonCreator
    public UserRequest(@JsonProperty("user") String user,
                       @JsonProperty("password") String password) {
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
