package com.gcourtet.glady.challenge.domain.services;

import com.gcourtet.glady.challenge.common.exception.NotFoundException;
import com.gcourtet.glady.challenge.common.exception.UserCreationException;
import com.gcourtet.glady.challenge.domain.data.Company;
import com.gcourtet.glady.challenge.domain.data.Deposit;
import com.gcourtet.glady.challenge.domain.data.DepositType;
import com.gcourtet.glady.challenge.domain.data.User;
import com.gcourtet.glady.challenge.domain.port.out.CompanyRepository;
import com.gcourtet.glady.challenge.domain.port.out.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void should_throw_exception_if_user_company_not_found() {
        when(companyRepository.getCompany(any())).thenReturn(null);
        assertThrows(UserCreationException.class,
                () -> userService.createUser("name", -1L));
    }

    @ParameterizedTest(name = "Should create user [{0}] without companyId")
    @ArgumentsSource(UserCreationProvider.class)
    void should_return_created_user(final String userName) {
        var createdUser = User.builder()
                .name(userName)
                .build();

        final Long companyId = null;

        when(userRepository.createUser(any())).thenReturn(createdUser);

        var result = userService.createUser(userName, companyId);

        verify(userRepository, times(1)).createUser(any());
        assertThat(result).isEqualTo(createdUser);
    }

    @ParameterizedTest(name = "Should create user [{0}] with companyId {1}")
    @ArgumentsSource(UserCreationWithCompanyIdProvider.class)
    void should_return_created_company(final String userName,
                                       final Long companyId) {
        var createdUser = User.builder()
                .name(userName)
                .companyId(companyId)
                .build();

        var fakeCompany = Company.builder().employees(new ArrayList<>()).build();
        when(companyRepository.getCompany(any())).thenReturn(fakeCompany);
        when(userRepository.createUser(any())).thenReturn(createdUser);

        var result = userService.createUser(userName, companyId);

        var companyIdCaptor = ArgumentCaptor.forClass(Long.class);

        verify(companyRepository, times(1)).getCompany(companyIdCaptor.capture());
        verify(userRepository, times(1)).createUser(any());
        assertThat(companyId).isEqualTo(companyIdCaptor.getValue());
        assertThat(fakeCompany.getEmployees()).contains(createdUser);
        assertThat(result).isEqualTo(createdUser);
    }

    @Test
    void should_throw_exception_if_user_not_found() {
        when(userRepository.getUser(anyLong())).thenReturn(null);
        assertThrows(NotFoundException.class,
                () -> userService.getUser(1L));
    }

    @Test
    void should_return_user_if_found() {
        var user = mock(User.class);
        when(userRepository.getUser(anyLong())).thenReturn(user);

        var id = 45678L;

        var result = userService.getUser(id);

        verify(userRepository, times(1)).getUser(id);
        assertThat(result).isEqualTo(user);
    }

    @Test
    void should_throw_not_found_exception_if_user_not_found_when_getting_user_balances() {
        when(userRepository.getUser(anyLong())).thenReturn(null);

        assertThrows(NotFoundException.class,
                () -> userService.getUserBalance(1L));
    }

    @Test
    void should_return_user_balances() {
        var amountGift = 199.90;
        var amountMeal = 800.05;

        var depositGift = Deposit.builder()
                .type(DepositType.GIFT)
                .value(amountGift)
                .expirationDate(LocalDate.MAX)
                .build();

        var depositMeal = Deposit.builder()
                .type(DepositType.MEAL)
                .value(amountMeal)
                .expirationDate(LocalDate.MAX)
                .build();

        var depositExpiredMeal = Deposit.builder()
                .type(DepositType.MEAL)
                .value(amountGift)
                .expirationDate(LocalDate.MIN)
                .build();

        var depositExpiredGift = Deposit.builder()
                .type(DepositType.GIFT)
                .value(amountMeal)
                .expirationDate(LocalDate.MIN)
                .build();

        var user = User.builder()
                .deposits(List.of(depositGift, depositMeal, depositExpiredMeal, depositExpiredGift))
                .build();

        when(userRepository.getUser(anyLong())).thenReturn(user);

        var id = 45678L;

        var result = userService.getUserBalance(id);

        var expectedResult = Map.of("TOTAL", amountGift + amountMeal,
                DepositType.GIFT.name(), amountGift,
                DepositType.MEAL.name(), amountMeal);

        verify(userRepository, times(1)).getUser(id);
        assertThat(result).isEqualTo(expectedResult);
    }

    private static class UserCreationProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            return Stream.of(
                    Arguments.of("", null),
                    Arguments.of("Jane Doe", null)
            );
        }
    }

    private static class UserCreationWithCompanyIdProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            return Stream.of(
                    Arguments.of("", 1L),
                    Arguments.of("Jane Doe", 2L)
            );
        }
    }
}
