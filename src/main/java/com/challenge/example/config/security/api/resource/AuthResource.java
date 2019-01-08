package com.challenge.example.config.security.api.resource;

import com.challenge.example.config.security.api.domain.AuthTokenInfo;
import com.challenge.example.config.security.api.domain.AuthToken;
import com.challenge.example.config.security.api.domain.Login;
import com.challenge.example.config.security.enums.Role;
import com.challenge.example.config.security.service.impl.AuthTokenService;
import com.challenge.example.user.entity.User;
import com.challenge.example.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Path("/")
public class AuthResource {

    private AuthenticationManager manager;
    private AuthTokenService service;
    private UserService userService;

    @Autowired
    public AuthResource(AuthenticationManager manager,
                        AuthTokenService service,
                        UserService userService) {
        this.manager = manager;
        this.service = service;
        this.userService = userService;
    }

    @POST
    @Path("/signin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Valid @NotNull(message = "Missing Fields") Login login) {

        final Authentication toBeAuth =
                new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());
        final Authentication auth = this.manager.authenticate(toBeAuth);
        SecurityContextHolder.getContext().setAuthentication(auth);

        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final Set<Role> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(grantedAuthority -> Role.valueOf(grantedAuthority.toString()))
                .collect(Collectors.toSet());

        final String token = service.getToken(username, authorities);
        final AuthToken authToken = new AuthToken();
        authToken.setToken(token);

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
