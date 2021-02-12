package com.osvalr.minesweeper.controller;

import com.osvalr.minesweeper.controller.dto.LoginResponse;
import com.osvalr.minesweeper.controller.dto.UserRequest;
import com.osvalr.minesweeper.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.SwaggerDefinition;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@CrossOrigin
@RestController
public class AuthController {
    private final UserService userService;

    @Inject
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(
            value="Validates a user & password and generates a token for the session",
    notes="Token does not expires"
    )
    @PostMapping("/users/auth")
    public ResponseEntity<LoginResponse> login(
            @ApiParam(
                    value = "User and password to login",
                    example = "{'user': 'user2020', 'password': '#P4sswd.'}",
                    required = true
            )
            @RequestBody UserRequest userRequest) {
        String token = userService.login(userRequest.getUser(), userRequest.getPassword());
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @ApiOperation(
            value = "Register a new user"
    )
    @PostMapping("/users/signup")
    public ResponseEntity<?> signUp(
            @ApiParam(
                    value = "User to be registered and the password",
                    example = "{'user': 'user2020', 'password': '#P4sswd.'}",
                    required = true
            )
            @RequestBody UserRequest userRequest) {
        userService.signUp(userRequest.getUser(), userRequest.getPassword());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
