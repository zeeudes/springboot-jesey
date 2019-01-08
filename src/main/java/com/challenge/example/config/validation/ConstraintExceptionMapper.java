package com.challenge.example.config.validation;

import com.challenge.example.pojo.MessageError;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConstraintExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(final ConstraintViolationException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                       .entity(prepareMessage(exception))
                       .type(MediaType.APPLICATION_JSON)
                       .build();
    }

    private MessageError prepareMessage(ConstraintViolationException exception) {
        String error = exception.getConstraintViolations().stream().findFirst().get().getMessage();
        return new MessageError(error, Response.Status.BAD_REQUEST.getStatusCode());
    }
}
