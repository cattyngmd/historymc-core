package ru.historymc.core.command.exception;

public class InvalidUsageException extends CommandException {
  public InvalidUsageException() {
    this(null);
  }

  public InvalidUsageException(String message) {
        super(message);
    }
}
