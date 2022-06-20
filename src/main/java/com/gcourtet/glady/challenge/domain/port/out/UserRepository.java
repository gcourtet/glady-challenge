package com.gcourtet.glady.challenge.domain.port.out;

import com.gcourtet.glady.challenge.domain.data.User;

public interface UserRepository {

    User createUser(final User userToCreate);

    User getUser(final Long userId);
}
