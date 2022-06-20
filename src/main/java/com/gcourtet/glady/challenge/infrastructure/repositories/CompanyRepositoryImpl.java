package com.gcourtet.glady.challenge.infrastructure.repositories;

import com.gcourtet.glady.challenge.domain.data.Company;
import com.gcourtet.glady.challenge.domain.port.out.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class CompanyRepositoryImpl implements CompanyRepository {

    private Map<Long, Company> companies = new HashMap<>();

    private long latestId = 1;

    @Override
    public Company createCompany(final Company companyToCreate) {
        companyToCreate.setId(latestId);
        companies.put(latestId++, companyToCreate);
        log.info("Company {} has been created.",
                companyToCreate);
        return companyToCreate;
    }

    @Override
    public Company getCompany(final Long companyId) {
        return companies.get(companyId);
    }

    /* WARNING - ONLY USE THE FOLLOWING METHODS FOR TESTING */

    public Map<Long, Company> getCompanies() {
        return new HashMap<>(companies);
    }

    public void setCompanies(final Map<Long, Company> companiesToSet) {
        this.companies = new HashMap<>(companiesToSet);
    }
}
