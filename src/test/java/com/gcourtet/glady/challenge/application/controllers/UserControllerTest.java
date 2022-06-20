package com.gcourtet.glady.challenge.application.controllers;

import com.gcourtet.glady.challenge.application.data.in.UserCreationRequest;
import com.gcourtet.glady.challenge.domain.data.User;
import com.gcourtet.glady.challenge.domain.port.in.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void should_create_user_when_asked() {
        var userCreated = mock(User.class);
        var nameCaptor = ArgumentCaptor.forClass(String.class);
        var companyIdCaptor = ArgumentCaptor.forClass(Long.class);
        when(userService.createUser(anyString(), anyLong())).thenReturn(userCreated);

        var companyId = 1L;
        var name = "John Doe";
        var request = UserCreationRequest.builder()
                .companyId(companyId)
                .name(name)
                .build();

        var result = userController.createUser(request);

        verify(userService, times(1)).createUser(nameCaptor.capture(),
                companyIdCaptor.capture());

        assertThat(result).isEqualTo(userCreated);
        assertThat(companyId).isEqualTo(companyIdCaptor.getValue());
        assertThat(name).isEqualTo(nameCaptor.getValue());
    }

    @Test
    void should_return_user() {
        var user = mock(User.class);
        var idCaptor = ArgumentCaptor.forClass(Long.class);
        when(userService.getUser(anyLong())).thenReturn(user);

        var id = 123L;

        var result = userController.getUser(id);

        verify(userService, times(1)).getUser(idCaptor.capture());

        assertThat(result).isEqualTo(user);
        assertThat(id).isEqualTo(idCaptor.getValue());
    }

}
