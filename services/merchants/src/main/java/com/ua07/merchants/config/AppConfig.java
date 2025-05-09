package com.ua07.merchants.config;

import com.ua07.shared.command.CommandExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public CommandExecutor commandExecutor() {
        return new CommandExecutor();
    }

}
