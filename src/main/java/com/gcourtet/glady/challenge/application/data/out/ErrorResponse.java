package com.gcourtet.glady.challenge.application.data.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class ErrorResponse {
    private String message;
    private int status;
}
