package com.challenge.example.exception;

import com.challenge.example.pojo.MessageError;
import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class APIException extends WebApplicationException {
    @Getter @Setter
    private Status status;

    @Setter
    private String message;

    public APIException(Status status, String message) {
        super(Response.status(status.getStatusCode())
                .entity(new MessageError(message, status.getStatusCode()))
                .type(MediaType.APPLICATION_JSON_TYPE).build()
        );
        this.status = status;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
