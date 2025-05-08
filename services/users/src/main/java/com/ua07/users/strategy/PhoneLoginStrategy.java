package com.ua07.users.strategy;

import com.ua07.users.services.AuthService;
import com.ua07.users.dtos.LoginRequest;  // Import LoginRequest DTO
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PhoneLoginStrategy implements LoginStrategy {

    private final AuthService authService;

    @Autowired
    public PhoneLoginStrategy(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public String authenticate(String identifier, String password) {
        // Create LoginRequest object and pass it to authService
        LoginRequest loginRequest = new LoginRequest(identifier, password);
        return authService.login(loginRequest);
    }
}
