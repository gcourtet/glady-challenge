package com.gcourtet.glady.challenge.infrastructure.repositories;

import com.gcourtet.glady.challenge.domain.data.User;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class UserRepositoryImplTest {

    private UserRepositoryImpl userRepository = new UserRepositoryImpl();

    @Test
    void should_add_user_to_memory_on_creation_with_no_user_in_memory() {
        var userToCreate = mock(User.class);
        userRepository.createUser(userToCreate);

        assertThat(userRepository.getUsers().values()).containsExactly(userToCreate);
    }

    @Test
    void should_add_user_to_memory_on_creation_with_users_already_in_memory() {
        var existingUser = mock(User.class);
        userRepository.setUsers(Map.of(0L, existingUser));

        var userToCreate = mock(User.class);
        userRepository.createUser(userToCreate);

        assertThat(userRepository.getUsers().values()).containsExactlyInAnyOrder(existingUser, userToCreate);
    }

    @Test
    void should_return_null_if_user_not_found_in_memory() {
        var existingUser = mock(User.class);
        userRepository.setUsers(Map.of(1L, existingUser));

        var user = userRepository.getUser(123L);

        assertThat(user).isNull();
    }

    @Test
    void should_return_user_if_found_in_memory() {
        var id = 1987L;
        var existingUser = mock(User.class);
        userRepository.setUsers(Map.of(id, existingUser));

        var user = userRepository.getUser(id);

        assertThat(user).isEqualTo(existingUser);
    }
}
