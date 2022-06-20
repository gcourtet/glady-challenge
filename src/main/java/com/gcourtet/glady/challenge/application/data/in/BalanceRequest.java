package com.gcourtet.glady.challenge.application.data.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BalanceRequest {
    @NotNull
    @Min(value = 0)
    private double amount;
}
