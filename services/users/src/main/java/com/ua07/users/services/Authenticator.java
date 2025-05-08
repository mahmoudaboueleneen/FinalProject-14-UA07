package com.ua07.users.services;

import com.ua07.users.dtos.LoginRequest;
import com.ua07.users.strategy.EmailLoginStrategy;
import com.ua07.users.strategy.PhoneLoginStrategy;
import com.ua07.users.strategy.LoginStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Authenticator {

    private final EmailLoginStrategy emailLoginStrategy;
    private final PhoneLoginStrategy phoneLoginStrategy;

    @Autowired
    public Authenticator(EmailLoginStrategy emailLoginStrategy, PhoneLoginStrategy phoneLoginStrategy) {
        this.emailLoginStrategy = emailLoginStrategy;
        this.phoneLoginStrategy = phoneLoginStrategy;
    }

    public String login(LoginRequest request) {
        // Determine which strategy to use based on the identifier (email or phone)
        if (request.getIdentifier().contains("@")) {
            return emailLoginStrategy.authenticate(request.getIdentifier(), request.getPassword());
        } else {
            return phoneLoginStrategy.authenticate(request.getIdentifier(), request.getPassword());
        }
    }
}
