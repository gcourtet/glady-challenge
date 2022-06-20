package com.gcourtet.glady.challenge.domain.services;

import com.gcourtet.glady.challenge.common.exception.DepositException;
import com.gcourtet.glady.challenge.common.exception.NotFoundException;
import com.gcourtet.glady.challenge.domain.data.Deposit;
import com.gcourtet.glady.challenge.domain.data.DepositType;
import com.gcourtet.glady.challenge.domain.port.in.DepositService;
import com.gcourtet.glady.challenge.domain.port.out.CompanyRepository;
import com.gcourtet.glady.challenge.domain.port.out.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class DepositServiceImpl implements DepositService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    @Override
    public double createDeposit(final Long companyId,
                                final double amountToAdd,
                                final DepositType depositType,
                                final Long userId) {
        var company = companyRepository.getCompany(companyId);
        if (null == company) {
            var message = String.format("Unable to process deposit as no company was found for id: %d", companyId);
            log.error(message);
            throw new NotFoundException(message);
        }

        var user = userRepository.getUser(userId);
        if (null == user) {
            var message = String.format("Unable to process deposit as no company was found for id: %d", userId);
            log.error(message);
            throw new NotFoundException(message);
        }

        var companyBalance = company.getBalance();
        checkCompanyBalance(companyId, amountToAdd, companyBalance);

        var deposit = Deposit.builder()
                .id(UUID.randomUUID())
                .issueDate(LocalDate.now())
                .type(depositType)
                .value(amountToAdd)
                .expirationDate(getExpirationDate(depositType))
                .build();

        user.getDeposits().add(deposit);
        company.setBalance(companyBalance - amountToAdd);

        log.info("Deposit {} has been made.",
                deposit);

        return user.getBalanceForType(depositType);
    }

    private void checkCompanyBalance(Long companyId, double amountToAdd, double companyBalance) {
        if (companyBalance < amountToAdd) {
            var message = String.format("Unable to process deposit as company's balance is too low for company %d (Needed = %.2f - Available = %.2f",
                    companyId,
                    amountToAdd,
                    companyBalance);
            log.error(message);
            throw new DepositException(message);
        }
    }

    public LocalDate getExpirationDate(final DepositType depositType) {
        var today = LocalDate.now();
        return depositType.equals(DepositType.MEAL) ?
                LocalDate.of(today.getYear() + 1,
                        2,
                        28) :
                today.plusDays(365);
    }
}
