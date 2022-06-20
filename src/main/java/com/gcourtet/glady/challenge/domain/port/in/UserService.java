package com.gcourtet.glady.challenge.domain.port.in;

import com.gcourtet.glady.challenge.domain.data.User;

import java.util.Map;

public interface UserService {

    User createUser(final String name, final Long companyId);

    User getUser(final Long userId);

    Map<String, Double> getUserBalance(final Long userId);
}
