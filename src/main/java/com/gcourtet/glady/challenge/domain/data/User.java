package com.gcourtet.glady.challenge.domain.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class User {
    private Long id;
    private Long companyId;
    private String name;
    private List<Deposit> deposits;

    public double getBalanceForType(final DepositType depositType) {
        var today = LocalDate.now();

        if (null == deposits) {
            return 0;
        }

        return deposits.stream()
                .filter(deposit -> depositType.equals(deposit.getType()))
                .filter(deposit -> today.compareTo(deposit.getExpirationDate()) <= 0)
                .map(Deposit::getValue)
                .mapToDouble(d -> d)
                .sum();
    }
}
