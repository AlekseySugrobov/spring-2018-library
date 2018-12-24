package ru.otus.library.exception;

public class UserInputProcessException extends Exception {
    public UserInputProcessException(String message) {
        super(message);
    }

    public UserInputProcessException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
