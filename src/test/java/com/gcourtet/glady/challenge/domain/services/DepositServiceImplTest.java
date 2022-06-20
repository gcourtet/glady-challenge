package com.gcourtet.glady.challenge.domain.services;

import com.gcourtet.glady.challenge.common.exception.DepositException;
import com.gcourtet.glady.challenge.common.exception.NotFoundException;
import com.gcourtet.glady.challenge.domain.data.Company;
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
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Stream;

import static com.gcourtet.glady.challenge.domain.data.DepositType.GIFT;
import static com.gcourtet.glady.challenge.domain.data.DepositType.MEAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepositServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DepositServiceImpl depositService;

    @Test
    void should_throw_not_found_exception_if_company_not_found() {
        when(companyRepository.getCompany(anyLong())).thenReturn(null);
        assertThrows(NotFoundException.class,
                () -> depositService.createDeposit(1L, 10.0, MEAL, 2L));
    }

    @Test
    void should_throw_not_found_exception_if_user_not_found() {
        when(companyRepository.getCompany(anyLong())).thenReturn(mock(Company.class));
        when(userRepository.getUser(anyLong())).thenReturn(null);
        assertThrows(NotFoundException.class,
                () -> depositService.createDeposit(1L, 10.0, GIFT, 2L));
    }

    @Test
    void should_throw_deposit_exception_if_company_balance_is_too_low() {
        var company = Company.builder()
                .balance(1.0)
                .build();
        when(companyRepository.getCompany(anyLong())).thenReturn(company);
        when(userRepository.getUser(anyLong())).thenReturn(mock(User.class));
        assertThrows(DepositException.class,
                () -> depositService.createDeposit(1L, 10.0, GIFT, 2L));
    }

    @ParameterizedTest(name = "Should create deposit for type {0}")
    @EnumSource(DepositType.class)
    void should_return_balance_for_type(final DepositType depositType) {
        var company = Company.builder()
                .balance(10000000.0)
                .build();
        var user = User.builder()
                        .deposits(new ArrayList<>()).build();
        when(companyRepository.getCompany(anyLong())).thenReturn(company);
        when(userRepository.getUser(anyLong())).thenReturn(user);

        var depositAmount = 10.0;

        var result = depositService.createDeposit(1L, depositAmount, depositType, 2L);

        assertThat(result).isEqualTo(depositAmount);
    }

    @ParameterizedTest(name = "Should return {0} when expiration date asked for type {1}")
    @ArgumentsSource(DepositExpirationDateProvider.class)
    void should_return_correct_expiration_date(final LocalDate expectedDate,
                                               final DepositType depositType) {
        var expirationDate = depositService.getExpirationDate(depositType);
        assertThat(expirationDate).isEqualTo(expectedDate);
    }

    private static class DepositExpirationDateProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            var today = LocalDate.now();
            return Stream.of(
                    Arguments.of(LocalDate.of(today.getYear() + 1, 2, 28), MEAL),
                    Arguments.of(LocalDate.of(today.getYear() + 1, today.getMonth(), today.getDayOfMonth()), GIFT)
            );
        }
    }
}
