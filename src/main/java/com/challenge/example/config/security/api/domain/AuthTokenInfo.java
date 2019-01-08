package com.challenge.example.config.security.api.domain;

import com.challenge.example.config.security.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
public class AuthTokenInfo {

    private final String id;
    private final String username;
    private final Set<Role> authorities;
    private final ZonedDateTime creation;
    private final ZonedDateTime expiration;
    private final Integer refreshCount;
    private final Integer refreshLimit;

    public boolean mustBeUpdated() {
        return refreshCount < refreshLimit;
    }
}