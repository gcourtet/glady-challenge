package com.gcourtet.glady.challenge.domain.port.out;

import com.gcourtet.glady.challenge.domain.data.Company;

public interface CompanyRepository {
    Company createCompany(final Company company);
}
