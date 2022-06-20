package com.gcourtet.glady.challenge.application.handlers;

import com.gcourtet.glady.challenge.common.exception.UserCreationException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserCreationExceptionHandlerTest {

    private UserCreationExceptionHandler userCreationExceptionHandler = new UserCreationExceptionHandler();

    @Test
    void should_handle_user_creation_exception() {
        var exception = new UserCreationException("Error message");
        var response = userCreationExceptionHandler.userCreationExceptionHandler(exception);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
        assertEquals(exception.getMessage(), response.getBody().getMessage());
    }
}
