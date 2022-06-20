package com.gcourtet.glady.challenge.domain.services;

import com.gcourtet.glady.challenge.common.exception.UserCreationException;
import com.gcourtet.glady.challenge.domain.data.Company;
import com.gcourtet.glady.challenge.domain.data.User;
import com.gcourtet.glady.challenge.domain.port.in.UserService;
import com.gcourtet.glady.challenge.domain.port.out.CompanyRepository;
import com.gcourtet.glady.challenge.domain.port.out.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    @Override
    public User createUser(final String name,
                           final Long companyId) {
        Company company = null;
        if (null != companyId) {
            company = companyRepository.getCompany(companyId);
            if (null == company) {
                var message = String.format("Unable to create user as no company was found for id %d", companyId);
                throw new UserCreationException(message);
            }
        }

        var userToCreate = User.builder()
                .name(name)
                .companyId(companyId)
                .build();

        var user = userRepository.createUser(userToCreate);

        if (null != company) {
            company.getEmployees().add(user);
        }

        return user;
    }
}
