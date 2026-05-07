package com.santiwrld.backend.ExceptionHandlers;

public class ResourceNotFound extends RuntimeException {
    public ResourceNotFound(String message) {
        super(message);
    }
}
