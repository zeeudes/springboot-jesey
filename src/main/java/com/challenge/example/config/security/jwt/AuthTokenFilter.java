package com.challenge.example.config.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {

    private final AuthenticationManager manager;
    private final AuthenticationEntryPoint authEntryPoint;

    @Autowired
    public AuthTokenFilter(final AuthenticationManager manager,
                           final AuthenticationEntryPoint authEntryPoint) {
        this.manager = manager;
        this.authEntryPoint = authEntryPoint;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest req, final HttpServletResponse res,
                                    final FilterChain chain) throws ServletException, IOException {

        final String authTokenHeader = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (authTokenHeader != null && authTokenHeader.startsWith("Bearer ")) {

            try {

                final String authToken = authTokenHeader.substring(7);
                final Authentication auth = new JwtAuthToken(authToken);
                final Authentication authenticated = manager.authenticate(auth);

                final SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authenticated);
                SecurityContextHolder.setContext(context);

            } catch (AuthenticationException e) {
                SecurityContextHolder.clearContext();
                authEntryPoint.commence(req, res, e);
                return;
            }
        }

        chain.doFilter(req, res);
    }
}