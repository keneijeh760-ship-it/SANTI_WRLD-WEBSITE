package com.santiwrld.backend.ExceptionHandlers;

import lombok.Getter;

import java.time.Instant;

@Getter
public class ErrorResponse {
    private String message;
    private int status;
    private String timestamp;

    public ErrorResponse(String messgae, int status) {
        this.message = messgae;
        this.status = status;
        this.timestamp = Instant.now().toString();
    }
}
