package com.gcourtet.glady.challenge.domain.port.in;

import com.gcourtet.glady.challenge.domain.data.Company;

public interface CompanyService {
    Company createCompany(final String name, final double initialBalance);
}