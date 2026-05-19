package br.com.vitta.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        return Response
                .status(500)
                .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                .build();
    }
}