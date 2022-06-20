package com.gcourtet.glady.challenge.infrastructure.repositories;

import com.gcourtet.glady.challenge.domain.data.User;
import com.gcourtet.glady.challenge.domain.port.out.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class UserRepositoryImpl implements UserRepository {

    private Map<Long, User> users = new HashMap<>();

    private long latestId = 1;

    @Override
    public User createUser(final User userToCreate) {
        userToCreate.setId(latestId);
        users.put(latestId++, userToCreate);
        log.info("User {} has been created.",
                userToCreate);
        return userToCreate;
    }

    /* WARNING - ONLY USE THE FOLLOWING METHODS FOR TESTING */

    public Map<Long, User> getUsers() {
        return new HashMap<>(users);
    }

    public void setUsers(final Map<Long, User> usersToSet) {
        this.users = new HashMap<>(usersToSet);
    }
}
