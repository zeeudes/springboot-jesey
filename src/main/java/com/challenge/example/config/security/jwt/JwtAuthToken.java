package com.challenge.example.config.security.jwt;

import com.challenge.example.config.security.api.domain.AuthTokenInfo;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtAuthToken extends AbstractAuthenticationToken {

    private String authToken;
    private UserDetails details;
    private AuthTokenInfo authTokenInfo;

    public JwtAuthToken(final String authToken) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.authToken = authToken;
        this.setAuthenticated(false);
    }

    public JwtAuthToken(final UserDetails details, final AuthTokenInfo authTokenInfo,
                        final Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.eraseCredentials();
        this.details = details;
        this.authTokenInfo = authTokenInfo;
        super.setAuthenticated(true);
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted. Use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return authToken;
    }

    @Override
    public Object getPrincipal() {
        return this.details;
    }

    @Override
    public Object getDetails() {
        return authTokenInfo;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.authToken = null;
    }
}