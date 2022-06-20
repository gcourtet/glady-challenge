package com.gcourtet.glady.challenge.infrastructure.repositories;

import com.gcourtet.glady.challenge.domain.data.Company;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class CompanyRepositoryImplTest {

    private CompanyRepositoryImpl companyRepository = new CompanyRepositoryImpl();

    @Test
    void should_add_company_to_set_on_creation_with_no_companies_in_memory() {
        var companyToCreate = mock(Company.class);
        companyRepository.createCompany(companyToCreate);

        assertThat(companyRepository.getCompanies().values()).containsExactly(companyToCreate);
    }

    @Test
    void should_add_company_to_set_on_creation_with_companies_already_in_memory() {
        var existingCompany = mock(Company.class);
        companyRepository.setCompanies(Map.of(0L, existingCompany));

        var companyToCreate = mock(Company.class);
        companyRepository.createCompany(companyToCreate);

        assertThat(companyRepository.getCompanies().values()).containsExactlyInAnyOrder(existingCompany, companyToCreate);
    }

    @Test
    void should_return_null_if_company_not_found_in_memory() {
        var existingCompany = mock(Company.class);
        companyRepository.setCompanies(Map.of(1L, existingCompany));

        var company = companyRepository.getCompany(123L);

        assertThat(company).isNull();
    }

    @Test
    void should_return_company_if_found_in_memory() {
        var id = 1987L;
        var existingCompany = mock(Company.class);
        companyRepository.setCompanies(Map.of(id, existingCompany));

        var company = companyRepository.getCompany(id);

        assertThat(company).isEqualTo(existingCompany);
    }

}
