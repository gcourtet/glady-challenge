package com.gcourtet.glady.challenge.domain.services;

import com.gcourtet.glady.challenge.common.exception.CompanyCreationException;
import com.gcourtet.glady.challenge.domain.data.Company;
import com.gcourtet.glady.challenge.domain.port.in.CompanyService;
import com.gcourtet.glady.challenge.domain.port.out.CompanyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    public Company createCompany(final String companyName,
                                 final double initialBalance) {
        if (initialBalance < 0) {
            log.error("Can't create company {} with negative balance {}",
                    companyName,
                    initialBalance);
            throw new CompanyCreationException("Impossible to create company with a negative balance.");
        }

        var companyToCreate = Company.builder()
                .name(companyName)
                .employees(new ArrayList<>())
                .balance(initialBalance)
                .build();

        return companyRepository.createCompany(companyToCreate);
    }
}
