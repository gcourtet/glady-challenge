package com.gcourtet.glady.challenge.application.handlers;

import com.gcourtet.glady.challenge.common.exception.CompanyCreationException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class CompanyCreationExceptionHandlerTest {

    private CompanyCreationExceptionHandler companyCreationExceptionHandler = new CompanyCreationExceptionHandler();

    @Test
    void should_handle_company_creation_exception() {
        var exception = new CompanyCreationException("Error message");
        var response = companyCreationExceptionHandler.companyCreationExceptionHandler(exception);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
        assertEquals(exception.getMessage(), response.getBody().getMessage());
    }
}
