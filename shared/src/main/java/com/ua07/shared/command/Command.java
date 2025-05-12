package com.ua07.shared.command;

/**
 * Command interface for executing commands with a request and response.
 *
 * @param <T> the type of the command request
 * @param <Q> the type of the command response
 */
public interface Command<T extends CommandRequest, Q extends CommandResponse> {
    Q execute(T request);

    void undo();
}
