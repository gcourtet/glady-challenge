package com.gcourtet.glady.challenge.application;

import com.gcourtet.glady.challenge.application.data.in.CompanyCreationRequest;
import com.gcourtet.glady.challenge.domain.data.Company;
import com.gcourtet.glady.challenge.domain.port.in.CompanyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public Company createCompany(@Valid @RequestBody final CompanyCreationRequest companyCreationRequest) {
        log.info("Received request to create company: {}", companyCreationRequest);
        return this.companyService.createCompany(companyCreationRequest.getName(),
                companyCreationRequest.getInitialBalance());
    }
}
