package com.gcourtet.glady.challenge.domain.services;

import com.gcourtet.glady.challenge.common.exception.NotFoundException;
import com.gcourtet.glady.challenge.common.exception.UserCreationException;
import com.gcourtet.glady.challenge.domain.data.Company;
import com.gcourtet.glady.challenge.domain.data.DepositType;
import com.gcourtet.glady.challenge.domain.data.User;
import com.gcourtet.glady.challenge.domain.port.in.UserService;
import com.gcourtet.glady.challenge.domain.port.out.CompanyRepository;
import com.gcourtet.glady.challenge.domain.port.out.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
                .deposits(new ArrayList<>())
                .companyId(companyId)
                .build();

        var user = userRepository.createUser(userToCreate);

        if (null != company) {
            company.getEmployees().add(user);
        }

        return user;
    }

    @Override
    public User getUser(final Long userId) {
        var user = userRepository.getUser(userId);

        if (null == user) {
            var message = String.format("No user found for id %d", userId);
            log.error(message);
            throw new NotFoundException(message);
        }

        return user;
    }

    @Override
    public Map<String, Double> getUserBalance(final Long userId) {
        var user = userRepository.getUser(userId);

        if (null == user) {
            var message = String.format("No user found for id %d", userId);
            log.error(message);
            throw new NotFoundException(message);
        }

        Map<String, Double> balances = new HashMap<>();
        var giftBalance = user.getBalanceForType(DepositType.GIFT);
        var mealBalance = user.getBalanceForType(DepositType.MEAL);
        balances.put(DepositType.MEAL.name(), mealBalance);
        balances.put(DepositType.GIFT.name(), giftBalance);
        balances.put("TOTAL", mealBalance + giftBalance);

        return balances;
    }
}
