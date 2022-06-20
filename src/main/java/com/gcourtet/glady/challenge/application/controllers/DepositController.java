package com.gcourtet.glady.challenge.application.controllers;

import com.gcourtet.glady.challenge.application.data.in.DepositRequest;
import com.gcourtet.glady.challenge.application.data.out.SuccessMessage;
import com.gcourtet.glady.challenge.domain.port.in.DepositService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/deposits")
public class DepositController {

    private final DepositService depositService;

    @PostMapping("/new-deposit")
    public SuccessMessage createDeposit(@Valid @RequestBody final DepositRequest depositRequest)  {
        log.info("Received deposit request ({})", depositRequest);
        var amountToAdd = depositRequest.getAmount();
        var depositType = depositRequest.getType();
        var userId = depositRequest.getUserId();
        var companyId = depositRequest.getCompanyId();
        var message = String.format("%.2f has been added to %s balance for userId %d. New balance is %.2f for this type.",
                amountToAdd,
                depositType,
                userId,
                depositService.createDeposit(companyId,
                        amountToAdd,
                        depositType,
                        userId));

        return SuccessMessage.builder()
                .message(message)
                .build();
    }
}
