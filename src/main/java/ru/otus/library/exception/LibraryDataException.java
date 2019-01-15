package ru.otus.library.exception;

public class LibraryDataException extends RuntimeException {
    public LibraryDataException(String message) {
        super(message);
    }

    public LibraryDataException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
