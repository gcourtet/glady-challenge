package com.gcourtet.glady.challenge.application.controllers;

import com.gcourtet.glady.challenge.application.data.in.UserCreationRequest;
import com.gcourtet.glady.challenge.domain.data.User;
import com.gcourtet.glady.challenge.domain.port.in.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

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

    @GetMapping("/{userId}")
    public User getUser(@PathVariable final Long userId) {
        log.info("Received request to get user: {}", userId);
        return userService.getUser(userId);
    }

    @GetMapping("/{userId}/balance")
    public Map<String, Double> getUserBalance(@PathVariable final Long userId) {
        log.info("Received request to get balance for user: {}", userId);
        return userService.getUserBalance(userId);
    }
}
