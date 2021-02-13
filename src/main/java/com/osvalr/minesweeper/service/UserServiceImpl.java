package com.osvalr.minesweeper.service;

import com.osvalr.minesweeper.domain.User;
import com.osvalr.minesweeper.exception.InvalidCredentialsException;
import com.osvalr.minesweeper.exception.UserAlreadyExistsException;
import com.osvalr.minesweeper.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Inject
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            return DatatypeConverter
                    .printHexBinary(digest).toUpperCase();
        } catch (Exception e) {
            return "";
        }
    }

    @Transactional
    @Override
    public String login(String username, String password) {
        Optional<User> userOptional = userRepository.findByUser(username);
        if (!userOptional.isPresent()) {
            throw new InvalidCredentialsException();
        }
        User user = userOptional.get();

        if (!user.getPassword().equals(hashPassword(password))) {
            throw new InvalidCredentialsException();
        }
        user.setActiveToken(UUID.randomUUID().toString());
        return user.getActiveToken();
    }

    @Override
    public void signUp(String username, String password) {
        Optional<User> userOptional = userRepository.findByUser(username);
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistsException();
        }
        userRepository.save(new User(username, hashPassword(password)));
    }

    @Override
    public void logout(String token) {
        Optional<User> userOptional = userRepository.findByActiveToken(token);
        if (!userOptional.isPresent()){
            // do nothing?
            return;
        }
        User user = userOptional.get();
        user.setActiveToken(null);
        userRepository.save(user);
    }
}
