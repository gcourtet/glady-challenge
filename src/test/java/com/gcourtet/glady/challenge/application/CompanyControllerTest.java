package com.gcourtet.glady.challenge.application;

import com.gcourtet.glady.challenge.application.data.in.CompanyCreationRequest;
import com.gcourtet.glady.challenge.domain.data.Company;
import com.gcourtet.glady.challenge.domain.port.in.CompanyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyControllerTest {

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private CompanyController companyController;

    @Test
    void should_create_user_when_asked() {
        var companyCreated = mock(Company.class);
        var nameCaptor = ArgumentCaptor.forClass(String.class);
        var balanceCaptor = ArgumentCaptor.forClass(Double.class);
        when(companyService.createCompany(anyString(), anyDouble())).thenReturn(companyCreated);

        var initialBalance = 79.99;
        var name = "MyCompany";
        var request = CompanyCreationRequest.builder()
                .initialBalance(initialBalance)
                .name(name)
                .build();

        var result = companyController.createCompany(request);

        verify(companyService, times(1)).createCompany(nameCaptor.capture(),
                balanceCaptor.capture());

        assertThat(result).isEqualTo(companyCreated);
        assertThat(initialBalance).isEqualTo(balanceCaptor.getValue());
        assertThat(name).isEqualTo(nameCaptor.getValue());
    }

    @Test
    void should_return_company() {
        var company = mock(Company.class);
        var idCaptor = ArgumentCaptor.forClass(Long.class);
        when(companyService.getCompany(anyLong())).thenReturn(company);

        var id = 123L;

        var result = companyController.getCompany(id);

        verify(companyService, times(1)).getCompany(idCaptor.capture());

        assertThat(result).isEqualTo(company);
        assertThat(id).isEqualTo(idCaptor.getValue());
    }

}
