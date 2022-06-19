package com.gcourtet.glady.challenge.application.handlers;

import com.gcourtet.glady.challenge.application.data.out.ErrorResponse;
import com.gcourtet.glady.challenge.common.exception.CompanyCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CompanyCreationExceptionHandler {

    @ResponseBody
    @ExceptionHandler(CompanyCreationException.class)
    public ResponseEntity<ErrorResponse> companyCreationExceptionHandler(final CompanyCreationException ex) {
        var errorResponse = ErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
