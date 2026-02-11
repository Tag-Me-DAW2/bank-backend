package com.tagme.tagme_bank_back.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tagme.tagme_bank_back.domain.exception.NotFoundException;
import com.tagme.tagme_bank_back.domain.model.BankAccount;
import com.tagme.tagme_bank_back.domain.model.Movement;
import com.tagme.tagme_bank_back.domain.service.BankAccountService;
import com.tagme.tagme_bank_back.web.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.instancio.Select.field;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BankAccountController.class)
@AutoConfigureMockMvc(addFilters = false)
class BankAccountControllerTest {

    @MockitoBean
    private BankAccountService bankAccountService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    class GetBankAccountByIdTests {
        @DisplayName("Given existing id, when GET /bank-accounts/{id} is called, then return bank account")
        @Test
        void getBankAccountByIdSuccess() throws Exception {
            BankAccount bankAccount = Instancio.of(BankAccount.class)
                    .set(field(BankAccount::getId), 1L)
                    .set(field(BankAccount::getIban), "ES9121000418450200051332")
                    .set(field(BankAccount::getBalance), BigDecimal.valueOf(1000))
                    .set(field(BankAccount::getMovements), new ArrayList<Movement>())
                    .create();

            when(bankAccountService.getById(1L)).thenReturn(bankAccount);

            mockMvc.perform(get("/bank-accounts/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.iban").value("ES9121000418450200051332"));
        }

        @DisplayName("Given non-existing id, when GET /bank-accounts/{id} is called, then return 404")
        @Test
        void getBankAccountByIdNotFound() throws Exception {
            when(bankAccountService.getById(999L)).thenThrow(new NotFoundException("Bank account not found"));

            mockMvc.perform(get("/bank-accounts/999"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    class GetBankAccountByIbanTests {
        @DisplayName("Given existing iban, when GET /bank-accounts/iban/{iban} is called, then return bank account")
        @Test
        void getBankAccountByIbanSuccess() throws Exception {
            BankAccount bankAccount = Instancio.of(BankAccount.class)
                    .set(field(BankAccount::getId), 1L)
                    .set(field(BankAccount::getIban), "ES9121000418450200051332")
                    .set(field(BankAccount::getBalance), BigDecimal.valueOf(1000))
                    .set(field(BankAccount::getMovements), new ArrayList<Movement>())
                    .create();

            when(bankAccountService.getByIban("ES9121000418450200051332")).thenReturn(bankAccount);

            mockMvc.perform(get("/bank-accounts/iban/ES9121000418450200051332"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.iban").value("ES9121000418450200051332"));
        }

        @DisplayName("Given non-existing iban, when GET /bank-accounts/iban/{iban} is called, then return 404")
        @Test
        void getBankAccountByIbanNotFound() throws Exception {
            when(bankAccountService.getByIban("NONEXISTENT")).thenThrow(new NotFoundException("Bank account not found"));

            mockMvc.perform(get("/bank-accounts/iban/NONEXISTENT"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    class GetBankAccountByClientIdTests {
        @DisplayName("Given valid token and matching clientId, when GET /bank-accounts/client/{clientId} is called, then return accounts")
        @Test
        void getBankAccountByClientIdSuccess() throws Exception {
            BankAccount bankAccount = Instancio.of(BankAccount.class)
                    .set(field(BankAccount::getId), 1L)
                    .set(field(BankAccount::getIban), "ES9121000418450200051332")
                    .set(field(BankAccount::getBalance), BigDecimal.valueOf(1000))
                    .set(field(BankAccount::getMovements), new ArrayList<Movement>())
                    .create();

            Claims mockClaims = mock(Claims.class);
            when(mockClaims.get("clientId", Long.class)).thenReturn(1L);

            try (MockedStatic<JwtUtil> mockedJwtUtil = mockStatic(JwtUtil.class)) {
                mockedJwtUtil.when(() -> JwtUtil.parseClaims(anyString())).thenReturn(mockClaims);
                when(bankAccountService.getByClientId(1L)).thenReturn(List.of(bankAccount));

                mockMvc.perform(get("/bank-accounts/client/1")
                                .header("Authorization", "Bearer valid-token"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.length()").value(1));
            }
        }

        @DisplayName("Given token with different clientId, when GET /bank-accounts/client/{clientId} is called, then return 401")
        @Test
        void getBankAccountByClientIdUnauthorized() throws Exception {
            Claims mockClaims = mock(Claims.class);
            when(mockClaims.get("clientId", Long.class)).thenReturn(2L);

            try (MockedStatic<JwtUtil> mockedJwtUtil = mockStatic(JwtUtil.class)) {
                mockedJwtUtil.when(() -> JwtUtil.parseClaims(anyString())).thenReturn(mockClaims);

                mockMvc.perform(get("/bank-accounts/client/1")
                                .header("Authorization", "Bearer valid-token"))
                        .andExpect(status().isUnauthorized());
            }
        }
    }
}
