package com.ua07.users.strategies;

import com.ua07.shared.jwt.JwtService;
import com.ua07.users.models.User;
import com.ua07.users.repositories.UserRepository;
import com.ua07.users.dtos.LoginRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PhoneLoginStrategy implements LoginStrategy {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String login(LoginRequest request) {
        User user = userRepository.findByPhone(request.getIdentifier())
                .orElseThrow(() -> new EntityNotFoundException("Invalid Credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid Credentials");
        }

        return jwtService.generateToken(user.getId(), user.getRole());
    }
}
