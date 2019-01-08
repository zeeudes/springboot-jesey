package com.challenge.example.config.security.service.impl;

import com.challenge.example.config.security.api.domain.AuthTokenInfo;
import com.challenge.example.config.security.enums.Role;
import com.challenge.example.config.security.exception.AuthTokenException;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TokenParser {

    private final JwtInfo info;

    public TokenParser(final JwtInfo info) {
        this.info = info;
    }

    public AuthTokenInfo fromString(String token) {

        try {

            final Claims claims = Jwts.parser()
                    .setSigningKey(info.getSecret())
                    .requireAudience(info.getAudience())
                    .setAllowedClockSkewSeconds(info.getClockSkew())
                    .parseClaimsJws(token)
                    .getBody();

            return new AuthTokenInfo(
                    getId(claims),
                    getUsername(claims),
                    getAuthorities(claims),
                    getCreation(claims),
                    getExpiration(claims),
                    getRefreshCount(claims),
                    getRefreshLimit(claims)
            );

        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException e) {
            throw new AuthTokenException("Invalid token", e);
        } catch (ExpiredJwtException e) {
            throw new AuthTokenException("Expired token", e);
        } catch (InvalidClaimException e) {
            throw new AuthTokenException("Invalid value for claim \"" + e.getClaimName() + "\"", e);
        } catch (Exception e) {
            throw new AuthTokenException("Invalid token", e);
        }
    }

    private String getId(@NotNull Claims claims) {
        return (String) claims.get(Claims.ID);
    }

    private String getUsername(@NotNull Claims claims) {
        return claims.getSubject();
    }

    private Set<Role> getAuthorities(@NotNull Claims claims) {
        final List<String> roles = (List<String>) claims.getOrDefault(info.getAuthoritiesClaimName(), Collections.EMPTY_LIST);
        return roles.stream().map(Role::valueOf).collect(Collectors.toSet());
    }

    private ZonedDateTime getCreation(@NotNull Claims claims) {
        return ZonedDateTime.ofInstant(claims.getIssuedAt().toInstant(), ZoneId.systemDefault());
    }

    private ZonedDateTime getExpiration(@NotNull Claims claims) {
        return ZonedDateTime.ofInstant(claims.getExpiration().toInstant(), ZoneId.systemDefault());
    }

    private Integer getRefreshCount(@NotNull Claims claims) {
        return (Integer) claims.get(info.getRefreshCountClaimName());
    }

    private Integer getRefreshLimit(@NotNull Claims claims) {
        return (Integer) claims.get(info.getRefreshLimitClaimName());
    }
}
