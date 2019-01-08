package com.challenge.example.config;

import com.challenge.example.config.security.api.exceptionmapper.AccessDeniedExceptionMapper;
import com.challenge.example.config.security.api.exceptionmapper.AuthExceptionMapper;
import com.challenge.example.config.security.api.exceptionmapper.AuthTokenRefresExceptionMapper;
import com.challenge.example.config.security.api.resource.AuthResource;
import com.challenge.example.config.validation.ConstraintExceptionMapper;
import com.challenge.example.user.api.resource.UserResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {

        register(AuthResource.class);
        register(UserResource.class);

        register(AccessDeniedExceptionMapper.class);
        register(AuthExceptionMapper.class);
        register(AuthTokenRefresExceptionMapper.class);
        register(ConstraintExceptionMapper.class);
    }
}