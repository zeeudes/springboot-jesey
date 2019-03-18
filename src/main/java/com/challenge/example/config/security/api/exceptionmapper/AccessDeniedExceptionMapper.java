package com.challenge.example.config.security.api.exceptionmapper;

import com.challenge.example.pojo.MessageError;
import org.springframework.security.access.AccessDeniedException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AccessDeniedExceptionMapper implements ExceptionMapper<AccessDeniedException> {

    @Context
    private UriInfo info;

    @Override
    public Response toResponse(final AccessDeniedException exception) {

        final Status status = Status.FORBIDDEN;

        final MessageError error = new MessageError();
        error.setErrorCode(status.getStatusCode());
        error.setMessage("You don't have enough permissions to perform this action");

        return Response.status(status).entity(error).type(MediaType.APPLICATION_JSON).build();
    }
}