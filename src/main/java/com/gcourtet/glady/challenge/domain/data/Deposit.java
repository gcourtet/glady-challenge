package com.gcourtet.glady.challenge.domain.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Deposit {
    private UUID id;
    private LocalDate issueDate;
    private LocalDate expirationDate;
    private DepositType type;
    private Double value;
}
