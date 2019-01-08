package com.challenge.example.config.security.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthTokenException extends AuthenticationException {

    public AuthTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
