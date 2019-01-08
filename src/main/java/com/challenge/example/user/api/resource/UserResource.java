package com.challenge.example.user.api.resource;

import com.challenge.example.config.security.api.domain.AuthToken;
import com.challenge.example.exception.APIException;
import com.challenge.example.user.api.dto.UserDTO;
import com.challenge.example.user.entity.User;
import com.challenge.example.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;

@Path("/")
@RestController
public class UserResource {

    private UserService service;

    @Autowired
    public UserResource(UserService userService) {
        this.service = userService;
    }

    @POST
    @Path("/signup")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response signup(@Valid @NotNull(message = "Missing Fields") UserDTO user) {

        if(Objects.nonNull(service.findByEmail(user.getEmail()))) {
            throw new APIException(Response.Status.BAD_REQUEST, "E-mail already exists");
        }

        final AuthToken authToken = service.save(user);
        return Response.ok(authToken).build();
    }

    @GET
    @Path("/me")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMe() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = service.findByEmail(auth.getName());
        UserDTO userDTO = UserDTO.toUserDTO(user);
        userDTO.setPassword(null);
        return Response.ok(userDTO).build();
    }
}
