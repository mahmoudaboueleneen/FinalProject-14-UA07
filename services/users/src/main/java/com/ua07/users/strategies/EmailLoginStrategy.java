package com.ua07.users.strategies;

import com.ua07.shared.jwt.JwtService;
import com.ua07.users.dtos.LoginRequest;
import com.ua07.users.models.User;
import com.ua07.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class EmailLoginStrategy implements LoginStrategy {

    @Autowired private JwtService jwtService;

    @Autowired private PasswordEncoder passwordEncoder;

    @Autowired private UserRepository userRepository;

    @Override
    public String login(LoginRequest request) {
        User user =
                userRepository
                        .findByEmail(request.getIdentifier())
                        .orElseThrow(
                                () ->
                                        new ResponseStatusException(
                                                HttpStatus.BAD_REQUEST, "Invalid Credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Credentials");
        }

        return jwtService.generateToken(user.getId(), user.getRole());
    }
}
