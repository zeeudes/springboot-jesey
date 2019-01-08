package com.challenge.example.config.security.exception;

public class AuthTokenRefreshException extends RuntimeException {

    public AuthTokenRefreshException(String message) {
        super(message);
    }

    public AuthTokenRefreshException(String message, Throwable cause) {
        super(message, cause);
    }
}
