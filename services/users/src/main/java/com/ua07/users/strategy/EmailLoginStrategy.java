package com.ua07.users.strategy;

import com.ua07.users.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailLoginStrategy implements LoginStrategy {

    private final AuthService authService;

    @Autowired
    public EmailLoginStrategy(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public String authenticate(String identifier, String password) {
        return authService.login(identifier, password);
    }
}
