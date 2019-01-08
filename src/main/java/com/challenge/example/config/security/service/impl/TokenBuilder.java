package com.challenge.example.config.security.service.impl;

import com.challenge.example.config.security.api.domain.AuthTokenInfo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenBuilder {

    private final JwtInfo settings;

    @Autowired
    public TokenBuilder(final JwtInfo settings) {
        this.settings = settings;
    }

    public String buildToken(AuthTokenInfo authTokenInfo) {

        return Jwts.builder()
                .setId(authTokenInfo.getId())
                .setIssuer(settings.getIssuer())
                .setAudience(settings.getAudience())
                .setSubject(authTokenInfo.getUsername())
                .setIssuedAt(Date.from(authTokenInfo.getCreation().toInstant()))
                .setExpiration(Date.from(authTokenInfo.getExpiration().toInstant()))
                .claim(settings.getAuthoritiesClaimName(), authTokenInfo.getAuthorities())
                .claim(settings.getRefreshCountClaimName(), authTokenInfo.getRefreshCount())
                .claim(settings.getRefreshLimitClaimName(), authTokenInfo.getRefreshLimit())
                .signWith(SignatureAlgorithm.HS256, settings.getSecret())
                .compact();
    }
}
