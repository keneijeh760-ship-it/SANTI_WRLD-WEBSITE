package com.santiwrld.backend.ExceptionHandlers;

public class IllegalArgumentException extends RuntimeException {
    public IllegalArgumentException(String message) {
        super(message);
    }
}
