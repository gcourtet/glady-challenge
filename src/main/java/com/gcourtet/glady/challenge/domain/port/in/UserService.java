package com.gcourtet.glady.challenge.domain.port.in;

import com.gcourtet.glady.challenge.domain.data.User;

public interface UserService {

    User createUser(final String name, final Long companyId);
}
