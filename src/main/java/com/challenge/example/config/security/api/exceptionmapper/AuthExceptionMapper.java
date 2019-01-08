package com.challenge.example.config.security.api.exceptionmapper;

import com.challenge.example.pojo.MessageError;
import org.springframework.security.core.AuthenticationException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthExceptionMapper implements ExceptionMapper<AuthenticationException> {

    @Context
    private UriInfo info;

    @Override
    public Response toResponse(AuthenticationException exception) {

        Status status = Status.FORBIDDEN;

        MessageError error = new MessageError();
        error.setErrorCode(status.getStatusCode());
        error.setMessage(exception.getMessage());

        return Response.status(status).entity(error).type(MediaType.APPLICATION_JSON).build();
    }
}