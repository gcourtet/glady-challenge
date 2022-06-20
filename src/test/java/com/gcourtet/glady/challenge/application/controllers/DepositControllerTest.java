package com.gcourtet.glady.challenge.application.controllers;

import com.gcourtet.glady.challenge.application.data.in.DepositRequest;
import com.gcourtet.glady.challenge.domain.data.DepositType;
import com.gcourtet.glady.challenge.domain.port.in.DepositService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepositControllerTest {

    @Mock
    private DepositService depositService;

    @InjectMocks
    private DepositController depositController;

    @ParameterizedTest(name = "Should create deposit of type {0}")
    @EnumSource(DepositType.class)
    void should_create_deposit(final DepositType depositType) {
        var companyId = 1L;
        var userId = 2L;
        var amountToAdd = 100.0;

        var amountReturned = 199.99;

        when(depositService.createDeposit(anyLong(), anyDouble(), any(), anyLong())).thenReturn(amountReturned);

        var request = DepositRequest.builder()
                .amount(amountToAdd)
                .companyId(companyId)
                .type(depositType)
                .userId(userId)
                .build();

        var result = depositController.createDeposit(request);

        var expected = String.format("%.2f has been added to %s balance for userId %d. New balance is %.2f for this type.",
                amountToAdd,
                depositType,
                userId,
                amountReturned);

        verify(depositService, times(1)).createDeposit(anyLong(), anyDouble(), any(), anyLong());
        assertThat(result.getMessage()).isEqualTo(expected);
    }
}
