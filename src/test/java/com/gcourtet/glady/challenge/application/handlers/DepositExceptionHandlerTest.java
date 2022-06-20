package com.gcourtet.glady.challenge.application.handlers;

import com.gcourtet.glady.challenge.common.exception.DepositException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DepositExceptionHandlerTest {
    private DepositExceptionHandler depositExceptionHandler = new DepositExceptionHandler();

    @Test
    void should_handle_deposit_exception() {
        var exception = new DepositException("Error message");
        var response = depositExceptionHandler.depositExceptionHandler(exception);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
        assertEquals(exception.getMessage(), response.getBody().getMessage());
    }
}
