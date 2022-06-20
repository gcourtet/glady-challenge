package com.gcourtet.glady.challenge.application.controllers;

import com.gcourtet.glady.challenge.application.data.in.UserCreationRequest;
import com.gcourtet.glady.challenge.domain.data.User;
import com.gcourtet.glady.challenge.domain.port.in.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public User createUser(@Valid @RequestBody final UserCreationRequest userCreationRequest) {
        log.info("Received request to create user: {}", userCreationRequest);
        return userService.createUser(userCreationRequest.getName(),
                userCreationRequest.getCompanyId());
    }
}
