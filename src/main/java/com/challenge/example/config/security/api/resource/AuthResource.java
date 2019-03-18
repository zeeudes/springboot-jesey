package com.challenge.example.config.security.api.resource;

import com.challenge.example.config.security.api.domain.AuthToken;
import com.challenge.example.config.security.api.domain.AuthTokenInfo;
import com.challenge.example.config.security.api.domain.Login;
import com.challenge.example.config.security.service.impl.AuthTokenService;
import com.challenge.example.user.entity.User;
import com.challenge.example.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/")
public class AuthResource {

    private final AuthTokenService service;
    private final UserService userService;

    @Autowired
    public AuthResource(final AuthTokenService service,
                        final UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @POST
    @Path("/signin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Valid @NotNull(message = "Missing Fields") final Login login) {

        final AuthToken authToken = this.userService.generateAuthToken(login);
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();

        final User user = this.userService.findByEmail(username);
        this.userService.registerLoginTime(user);

        return Response.ok(authToken).build();
    }

    @POST
    @Path("/refresh")
    @Produces(MediaType.APPLICATION_JSON)
    public Response refresh() {

        final AuthTokenInfo tokenDetails = (AuthTokenInfo)
                SecurityContextHolder.getContext().getAuthentication().getDetails();

        final String token = this.service.refreshAuthToken(tokenDetails);
        final AuthToken authToken = new AuthToken();
        authToken.setToken(token);

        return Response.ok(authToken).build();
    }
}
