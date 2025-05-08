package com.ua07.users.services;

import com.ua07.users.strategy.EmailLoginStrategy;
import com.ua07.users.strategy.PhoneLoginStrategy;
import com.ua07.users.strategy.LoginStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Authenticator {

    private LoginStrategy loginStrategy;

    public void setStrategy(LoginStrategy loginStrategy) {
        this.loginStrategy = loginStrategy;
    }

    public String login(String identifier, String password) {
        if (identifier.contains("@")) {
            setStrategy(new EmailLoginStrategy());
        } else {
            setStrategy(new PhoneLoginStrategy());
        }
        return loginStrategy.authenticate(identifier, password);
    }
}
