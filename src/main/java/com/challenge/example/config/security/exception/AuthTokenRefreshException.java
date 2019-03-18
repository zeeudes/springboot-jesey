package com.challenge.example.config.security.exception;

public class AuthTokenRefreshException extends RuntimeException {

    public AuthTokenRefreshException(final String message) {
        super(message);
    }

    public AuthTokenRefreshException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
