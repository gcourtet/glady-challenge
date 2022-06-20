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
public class UserCreationRequest {
    private Long companyId;
    @NotBlank
    @NotNull
    private String name;
}
