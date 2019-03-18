package com.challenge.example.config.security.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthTokenException extends AuthenticationException {

    public AuthTokenException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
