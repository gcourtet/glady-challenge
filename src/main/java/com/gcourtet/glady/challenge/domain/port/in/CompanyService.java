package com.gcourtet.glady.challenge.domain.port.in;

import com.gcourtet.glady.challenge.domain.data.Company;

public interface CompanyService {
    Company createCompany(final String name, final Double initialBalance);

    Company getCompany(final Long companyId);

    Double addToBalance(final Long companyId, final Double amountToAdd);
}
