package com.challenge.example.config.security.service.impl;
import com.challenge.example.config.security.api.domain.AuthTokenInfo;
import com.challenge.example.config.security.enums.Role;
import com.challenge.example.config.security.exception.AuthTokenRefreshException;
import com.challenge.example.config.security.service.IAuthTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
public class AuthTokenService implements IAuthTokenService {

    @Value("${authentication.jwt.validFor}")
    private Long limit;

    @Value("${authentication.jwt.refreshLimit}")
    private Integer refresh;

    private final TokenBuilder builder;
    private final TokenParser tokenParser;

    public AuthTokenService(final TokenBuilder builder, final TokenParser tokenParser) {
        this.builder = builder;
        this.tokenParser = tokenParser;
    }

    @Override
    public String getToken(final String username, final Set<Role> roles) {

        final String id = getId();
        final ZonedDateTime creation = ZonedDateTime.now();
        final ZonedDateTime expirationDate = getExpirationDate(creation);

        final AuthTokenInfo info = new AuthTokenInfo(
                id, username, roles, creation, expirationDate, 0, this.refresh);

        return builder.buildToken(info);
    }

    @Override
    public AuthTokenInfo parseToken(final String token) {
        return tokenParser.fromString(token);
    }

    @Override
    public String refreshAuthToken(final AuthTokenInfo currentTokenInfo) {

        if (Objects.equals(Boolean.FALSE, currentTokenInfo.mustBeUpdated())) {
            throw new AuthTokenRefreshException("This token cannot be refreshed.");
        }

        final ZonedDateTime creation = ZonedDateTime.now();
        final ZonedDateTime expirationDate = getExpirationDate(creation);

        final AuthTokenInfo info = new AuthTokenInfo(
                currentTokenInfo.getId(),
                currentTokenInfo.getUsername(),
                currentTokenInfo.getAuthorities(),
                creation,
                expirationDate,
                currentTokenInfo.getRefreshCount() + 1,
                this.refresh);

        return builder.buildToken(info);
    }

    private ZonedDateTime getExpirationDate(final ZonedDateTime creation) {
        return creation.plusSeconds(limit);
    }

    private String getId() {
        return UUID.randomUUID().toString();
    }
}
