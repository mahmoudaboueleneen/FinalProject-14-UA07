package com.ua07.users.strategies;

import com.ua07.users.dtos.LoginRequest;

public interface LoginStrategy {
    String login(LoginRequest request);
}
