package com.osvalr.minesweeper.service;

public interface UserService {
    String login(String user, String password);

    void signUp(String user, String password);

    void logout(String sessionId);
}
