package com.ua07.merchants.command;

import org.springframework.stereotype.Component;

@Component
public class CommandExecutor {
    public Object run(Command command) {
        return command.execute();
    }
}

