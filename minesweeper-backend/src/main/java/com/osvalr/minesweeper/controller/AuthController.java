package com.osvalr.minesweeper.controller;

import com.osvalr.minesweeper.controller.dto.LoginResponse;
import com.osvalr.minesweeper.controller.dto.UserRequest;
import com.osvalr.minesweeper.service.UserService;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/users/auth")
    public ResponseEntity<LoginResponse> login(@RequestBody UserRequest userRequest) {
        String token = userService.login(userRequest.getUser(), userRequest.getPassword());
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/users/signup")
    public ResponseEntity<?> signUp(@RequestBody UserRequest userRequest) {
        userService.signUp(userRequest.getUser(), userRequest.getPassword());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
