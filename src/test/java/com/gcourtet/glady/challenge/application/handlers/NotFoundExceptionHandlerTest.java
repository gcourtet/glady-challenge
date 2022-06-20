package com.gcourtet.glady.challenge.application.handlers;

import com.gcourtet.glady.challenge.common.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotFoundExceptionHandlerTest {
    private NotFoundExceptionHandler notFoundExceptionHandler = new NotFoundExceptionHandler();

    @Test
    void should_handle_not_found_exception() {
        var exception = new NotFoundException("Error message");
        var response = notFoundExceptionHandler.notFoundExceptionHandler(exception);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
        assertEquals(exception.getMessage(), response.getBody().getMessage());
    }
}
