package com.challenge.example.config.security.jwt;

import com.challenge.example.config.security.api.domain.AuthTokenInfo;
import com.challenge.example.config.security.service.impl.AuthTokenService;
import com.challenge.example.config.security.service.impl.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthProvider extends DaoAuthenticationProvider {

    private final UserDetailService detailService;
    private final AuthTokenService authTokenService;

    @Autowired
    public AuthProvider(final UserDetailService detailService,
                        final AuthTokenService authTokenService,
                        final PasswordEncoder encoder) {

        this.authTokenService = authTokenService;
        this.detailService = detailService;
        setPasswordEncoder(encoder);
        setUserDetailsService(detailService);
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {

        final String authToken = (String) auth.getCredentials();
        final AuthTokenInfo authTokenInfo = authTokenService.parseToken(authToken);
        final UserDetails details = this.detailService.loadUserByUsername(authTokenInfo.getUsername());

        return new JwtAuthToken(details, authTokenInfo, details.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthToken.class.isAssignableFrom(authentication));
    }
}