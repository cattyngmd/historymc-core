package ru.historymc.core.command.exception;

public class NoPlayerFoundException extends CommandException {
    public NoPlayerFoundException(String player) {
        super("Player %s is currently offline".formatted(player));
    }
}
