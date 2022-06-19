package com.gcourtet.glady.challenge.application.data.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CompanyCreationRequest {
    @NotNull
    @NotBlank
    private String name;
    private double initialBalance;
}
