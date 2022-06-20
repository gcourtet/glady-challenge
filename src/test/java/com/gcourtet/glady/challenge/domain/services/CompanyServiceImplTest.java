package com.gcourtet.glady.challenge.domain.services;

import com.gcourtet.glady.challenge.common.exception.CompanyCreationException;
import com.gcourtet.glady.challenge.common.exception.NotFoundException;
import com.gcourtet.glady.challenge.domain.data.Company;
import com.gcourtet.glady.challenge.domain.port.out.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {
    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyServiceImpl companyService;

    @Test
    void should_throw_exception_if_balance_is_negative() {
        assertThrows(CompanyCreationException.class,
                () -> companyService.createCompany("name", -1.0));
    }

    @ParameterizedTest(name = "Should create company [{0}] with balance {1}")
    @ArgumentsSource(CompanyCreationProvider.class)
    void should_return_created_company(final String companyName,
                                       final double initialBalance) {
        var createdCompany = Company.builder()
                .name(companyName)
                .balance(initialBalance)
                .build();
        when(companyRepository.createCompany(any())).thenReturn(createdCompany);

        var result = companyService.createCompany(companyName, initialBalance);

        verify(companyRepository, times(1)).createCompany(any());
        assertThat(result).isEqualTo(createdCompany);
    }

    @Test
    void should_throw_exception_if_company_not_found() {
        when(companyRepository.getCompany(anyLong())).thenReturn(null);
        assertThrows(NotFoundException.class,
                () -> companyService.getCompany(1L));
    }

    @Test
    void should_return_company_if_found() {
        var company = mock(Company.class);
        when(companyRepository.getCompany(anyLong())).thenReturn(company);

        var id = 45678L;

        var result = companyService.getCompany(id);

        verify(companyRepository, times(1)).getCompany(id);
        assertThat(result).isEqualTo(company);
    }

    @Test
    void should_throw_exception_if_company_not_found_when_adding_to_balance() {
        when(companyRepository.getCompany(anyLong())).thenReturn(null);
        assertThrows(NotFoundException.class,
                () -> companyService.addToBalance(1L, 20.9));
    }

    @Test
    void should_return_new_balance_when_adding_to_balance() {
        var company = mock(Company.class);
        company.setBalance(0.0);
        when(companyRepository.getCompany(anyLong())).thenReturn(company);

        var id = 45678L;
        var amountToAdd = 12345.67;

        var result = companyService.addToBalance(id, amountToAdd);

        verify(companyRepository, times(1)).getCompany(id);
        assertThat(result).isEqualTo(amountToAdd);
    }

    private static class CompanyCreationProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            return Stream.of(
                    Arguments.of("coolCompany", 0),
                    Arguments.of("", 10),
                    Arguments.of("prettyBigCompany", 10000.99)
            );
        }
    }
}
