package com.ua07.users.strategy;

public interface LoginStrategy {
    String authenticate(String identifier, String password);
}
