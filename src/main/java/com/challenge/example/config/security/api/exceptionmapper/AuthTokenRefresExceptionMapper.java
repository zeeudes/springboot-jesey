package com.challenge.example.config.security.api.exceptionmapper;
import com.challenge.example.config.security.exception.AuthTokenRefreshException;
import com.challenge.example.pojo.MessageError;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthTokenRefresExceptionMapper implements ExceptionMapper<AuthTokenRefreshException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(final AuthTokenRefreshException exception) {

        final Status status = Status.FORBIDDEN;

        final MessageError error = new MessageError();
        error.setErrorCode(status.getStatusCode());
        error.setMessage("The authentication token cannot be refreshed.");

        return Response.status(status).entity(error).type(MediaType.APPLICATION_JSON).build();
    }
}