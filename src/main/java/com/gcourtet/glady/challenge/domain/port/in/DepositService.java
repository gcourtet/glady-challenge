package com.gcourtet.glady.challenge.domain.port.in;

import com.gcourtet.glady.challenge.domain.data.DepositType;

public interface DepositService {
    double createDeposit(final Long companyId, final double amountToAdd, final DepositType depositType, final Long userId);
}
