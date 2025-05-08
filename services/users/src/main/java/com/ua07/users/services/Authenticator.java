package com.ua07.users.services;

import com.ua07.users.dtos.LoginRequest;
import com.ua07.users.strategies.LoginStrategy;
import org.springframework.stereotype.Service;

@Service
public class Authenticator {

    private LoginStrategy strategy;

    public void setStrategy(LoginStrategy strategy) {
        this.strategy = strategy;
    }

    public String login(LoginRequest request) {
        return strategy.login(request);
    }
}
