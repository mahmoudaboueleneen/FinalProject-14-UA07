package com.ua07.apigateway.config;

import com.ua07.shared.jwt.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public JwtService jwtService() {
        return new JwtService();
    }
}
