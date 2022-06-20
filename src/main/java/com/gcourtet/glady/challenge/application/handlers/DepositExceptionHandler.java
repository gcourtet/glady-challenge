package com.gcourtet.glady.challenge.application.handlers;

import com.gcourtet.glady.challenge.application.data.out.ErrorResponse;
import com.gcourtet.glady.challenge.common.exception.DepositException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class DepositExceptionHandler {

    @ResponseBody
    @ExceptionHandler(DepositException.class)
    public ResponseEntity<ErrorResponse> depositExceptionHandler(final DepositException ex) {
        var errorResponse = ErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
