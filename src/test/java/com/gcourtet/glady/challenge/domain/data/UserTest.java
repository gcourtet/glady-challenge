package com.gcourtet.glady.challenge.domain.data;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void should_return_0_if_empty_deposit_list() {
        var user = User.builder()
                .deposits(Collections.emptyList())
                .build();
        assertThat(user.getBalanceForType(DepositType.MEAL)).isZero();
        assertThat(user.getBalanceForType(DepositType.GIFT)).isZero();
    }

    @Test
    void should_return_0_if_null_deposit_list() {
        var user = User.builder()
                .build();
        assertThat(user.getBalanceForType(DepositType.MEAL)).isZero();
        assertThat(user.getBalanceForType(DepositType.GIFT)).isZero();
    }

    @Test
    void should_return_meal_balance() {
        var amount1 = 15.1;
        var amount2 = 46.5;
        var amountExpired = 90.6;
        var amountOtherType = 299.99;

        var deposit1 = Deposit.builder()
                .type(DepositType.MEAL)
                .value(amount1)
                .expirationDate(LocalDate.MAX)
                .build();

        var deposit2 = Deposit.builder()
                .type(DepositType.MEAL)
                .value(amount2)
                .expirationDate(LocalDate.MAX)
                .build();

        var depositExpired = Deposit.builder()
                .type(DepositType.MEAL)
                .value(amountExpired)
                .expirationDate(LocalDate.MIN)
                .build();

        var depositOtherType = Deposit.builder()
                .type(DepositType.GIFT)
                .value(amountOtherType)
                .expirationDate(LocalDate.MAX)
                .build();

        var user = User.builder()
                .deposits(List.of(deposit1, deposit2, depositExpired, depositOtherType))
                .build();

        assertThat(user.getBalanceForType(DepositType.MEAL)).isEqualTo(amount1 + amount2);
    }

    @Test
    void should_return_gift_balance() {
        var amount1 = 12.1;
        var amount2 = 23.5;
        var amountExpired = 18.6;
        var amountOtherType = 99.99;

        var deposit1 = Deposit.builder()
                .type(DepositType.GIFT)
                .value(amount1)
                .expirationDate(LocalDate.MAX)
                .build();

        var deposit2 = Deposit.builder()
                .type(DepositType.GIFT)
                .value(amount2)
                .expirationDate(LocalDate.MAX)
                .build();

        var depositExpired = Deposit.builder()
                .type(DepositType.GIFT)
                .value(amountExpired)
                .expirationDate(LocalDate.MIN)
                .build();

        var depositOtherType = Deposit.builder()
                .type(DepositType.MEAL)
                .value(amountOtherType)
                .expirationDate(LocalDate.MAX)
                .build();

        var user = User.builder()
                .deposits(List.of(deposit1, deposit2, depositExpired, depositOtherType))
                .build();

        assertThat(user.getBalanceForType(DepositType.GIFT)).isEqualTo(amount1 + amount2);
    }
}
