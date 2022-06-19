package com.gcourtet.glady.challenge.domain.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Deposit {
    private Long id;
    private LocalDate issueDate;
    private LocalDate expirationDate;
    private DepositType type;
    private double value;
}
