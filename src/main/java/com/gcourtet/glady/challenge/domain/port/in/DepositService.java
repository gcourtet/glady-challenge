package com.gcourtet.glady.challenge.domain.port.in;

import com.gcourtet.glady.challenge.domain.data.DepositType;

public interface DepositService {
    Double createDeposit(final Long companyId, final Double amountToAdd, final DepositType depositType, final Long userId);
}
