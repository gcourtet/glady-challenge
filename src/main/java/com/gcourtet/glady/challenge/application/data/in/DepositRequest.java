package com.gcourtet.glady.challenge.application.data.in;

import com.gcourtet.glady.challenge.domain.data.DepositType;
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
public class DepositRequest {
    @NotNull
    private Long companyId;
    @NotNull
    private Long userId;
    @NotNull
    @Min(value = 0)
    private double amount;
    @NotNull
    private DepositType type;
}
