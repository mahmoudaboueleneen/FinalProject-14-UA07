package com.ua07.shared.command;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Executes commands and supports undo functionality.
 * Note: This requires registration with the DI container, so create a
 * Config file in your module that returns a bean of this class.
 */
public class CommandExecutor {

    private final Deque<Command<?, ?>> history = new ConcurrentLinkedDeque<>();

    /**
     * Executes the given command with the provided request.
     *
     * @param command the command to execute
     * @param request the request data
     * @param <T>     the type of the request
     * @param <Q>     the type of the response
     * @return the result of the command execution
     */
    public <T extends CommandRequest, Q extends CommandResponse> Q execute(Command<T, Q> command, T request) {
        Q response = command.execute(request);
        history.push(command);  // Track for undo. Thread-safe.
        return response;
    }

    /**
     * Undoes the last executed command, if available.
     */
    public void undoLast() {
        Command<?, ?> lastCommand = history.poll(); // Thread-safe pop
        if (lastCommand != null) {
            try {
                lastCommand.undo();
            } catch (UnsupportedOperationException e) {
                System.err.println("Undo not supported for: " + lastCommand.getClass().getSimpleName());
            }
        } else {
            System.out.println("No command to undo.");
        }
    }

    /**
     * Clears the command history.
     */
    public void clearHistory() {
        history.clear();
    }
}
