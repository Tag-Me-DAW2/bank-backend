package com.tagme.tagme_bank_back.usecase.impl;

import com.tagme.tagme_bank_back.domain.dto.AuthDto;
import com.tagme.tagme_bank_back.domain.dto.PayDto;
import com.tagme.tagme_bank_back.domain.dto.TransferDto;
import com.tagme.tagme_bank_back.domain.dto.TransferPaymentDto;
import com.tagme.tagme_bank_back.domain.exception.InsufficientBalanceException;
import com.tagme.tagme_bank_back.domain.model.BankAccount;
import com.tagme.tagme_bank_back.domain.model.MovementOrigin;
import com.tagme.tagme_bank_back.domain.service.AuthService;
import com.tagme.tagme_bank_back.domain.service.BankAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TransferPaymentUseCase Tests")
class TransferPaymentUseCaseImplTest {

    @Mock
    private BankAccountService bankAccountService;

    @Mock
    private AuthService authService;

    @InjectMocks
    private TransferPaymentUseCaseImpl transferPaymentUseCase;

    private BankAccount originAccount;
    private BankAccount destinationAccount;
    private TransferPaymentDto paymentDto;

    @BeforeEach
    void setUp() {
        originAccount = new BankAccount(
                1L,
                "ES9121000418450200051335",
                BigDecimal.valueOf(1000),
                null,
                new ArrayList<>(),
                new ArrayList<>()
        );

        destinationAccount = new BankAccount(
                2L,
                "ES7921000813610123456789",
                BigDecimal.valueOf(500),
                null,
                new ArrayList<>(),
                new ArrayList<>()
        );

        paymentDto = new TransferPaymentDto(
                new AuthDto("testuser", "api-key-123"),
                new TransferDto("ES9121000418450200051335"),  // origin
                new TransferDto("ES7921000813610123456789"),  // destination
                new PayDto(BigDecimal.valueOf(100), "Test transfer")
        );
    }

    @Nested
    @DisplayName("execute Tests")
    class ExecuteTests {

        @Test
        @DisplayName("Given valid transfer data, when execute is called, then transfer is processed successfully")
        void executeSuccess() {
            // Arrange
            when(authService.isApiKeyValid(anyString())).thenReturn(true);
            when(bankAccountService.checkOwner(anyString(), anyString())).thenReturn(true);
            when(bankAccountService.getBalanceByIban("ES9121000418450200051335")).thenReturn(BigDecimal.valueOf(1000));
            when(bankAccountService.getByIban("ES9121000418450200051335")).thenReturn(originAccount);
            when(bankAccountService.getByIban("ES7921000813610123456789")).thenReturn(destinationAccount);
            doNothing().when(bankAccountService).withdrawByTransfer(any(), any(), anyString());
            doNothing().when(bankAccountService).deposit(any(), any(), any(MovementOrigin.class), anyString());

            // Act & Assert
            assertDoesNotThrow(() -> transferPaymentUseCase.execute(paymentDto));

            verify(authService).isApiKeyValid("api-key-123");
            verify(bankAccountService).checkOwner("ES9121000418450200051335", "testuser");
            verify(bankAccountService).withdrawByTransfer(eq(originAccount), eq(BigDecimal.valueOf(100)), eq("Test transfer"));
            verify(bankAccountService).deposit(eq(destinationAccount), eq(BigDecimal.valueOf(100)), eq(MovementOrigin.TRANSFER), eq("Test transfer"));
        }

        @Test
        @DisplayName("Given same origin and destination accounts, when execute is called, then throw IllegalArgumentException")
        void executeSameOriginAndDestination() {
            // Arrange - origin iban equals destination iban
            TransferPaymentDto sameAccountDto = new TransferPaymentDto(
                    new AuthDto("testuser", "api-key-123"),
                    new TransferDto("ES9121000418450200051335"),
                    new TransferDto("ES9121000418450200051335"),  // Same as origin
                    new PayDto(BigDecimal.valueOf(100), "Test transfer")
            );

            when(authService.isApiKeyValid(anyString())).thenReturn(true);

            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> transferPaymentUseCase.execute(sameAccountDto)
            );

            assertEquals("The origin and destination accounts cannot be the same", exception.getMessage());
        }

        @Test
        @DisplayName("Given insufficient balance, when execute is called, then throw InsufficientBalanceException")
        void executeInsufficientBalance() {
            // Arrange
            TransferPaymentDto largeTransferDto = new TransferPaymentDto(
                    new AuthDto("testuser", "api-key-123"),
                    new TransferDto("ES9121000418450200051335"),
                    new TransferDto("ES7921000813610123456789"),
                    new PayDto(BigDecimal.valueOf(5000), "Large transfer")  // More than balance
            );

            when(authService.isApiKeyValid(anyString())).thenReturn(true);
            when(bankAccountService.checkOwner(anyString(), anyString())).thenReturn(true);
            when(bankAccountService.getBalanceByIban("ES9121000418450200051335")).thenReturn(BigDecimal.valueOf(1000));

            // Act & Assert
            InsufficientBalanceException exception = assertThrows(
                    InsufficientBalanceException.class,
                    () -> transferPaymentUseCase.execute(largeTransferDto)
            );

            assertEquals("Insufficient balance", exception.getMessage());
        }

        @Test
        @DisplayName("Given invalid API key, when execute is called, then authorization fails")
        void executeInvalidApiKey() {
            // Arrange
            doThrow(new RuntimeException("Invalid API key"))
                    .when(authService).isApiKeyValid(anyString());

            // Act & Assert
            assertThrows(RuntimeException.class, () -> transferPaymentUseCase.execute(paymentDto));

            verify(authService).isApiKeyValid("api-key-123");
            verifyNoMoreInteractions(bankAccountService);
        }

        @Test
        @DisplayName("Given user not owner of account, when execute is called, then checkOwner fails")
        void executeNotOwner() {
            // Arrange
            when(authService.isApiKeyValid(anyString())).thenReturn(true);
            when(bankAccountService.checkOwner(anyString(), anyString()))
                    .thenThrow(new RuntimeException("User is not owner of account"));

            // Act & Assert
            assertThrows(RuntimeException.class, () -> transferPaymentUseCase.execute(paymentDto));

            verify(authService).isApiKeyValid("api-key-123");
            verify(bankAccountService).checkOwner("ES9121000418450200051335", "testuser");
        }

        @Test
        @DisplayName("Given zero amount transfer, when execute is called, then transfer succeeds if balance allows")
        void executeZeroAmount() {
            // Arrange
            TransferPaymentDto zeroTransferDto = new TransferPaymentDto(
                    new AuthDto("testuser", "api-key-123"),
                    new TransferDto("ES9121000418450200051335"),
                    new TransferDto("ES7921000813610123456789"),
                    new PayDto(BigDecimal.ZERO, "Zero transfer")
            );

            when(authService.isApiKeyValid(anyString())).thenReturn(true);
            when(bankAccountService.checkOwner(anyString(), anyString())).thenReturn(true);
            when(bankAccountService.getBalanceByIban("ES9121000418450200051335")).thenReturn(BigDecimal.valueOf(1000));
            when(bankAccountService.getByIban("ES9121000418450200051335")).thenReturn(originAccount);
            when(bankAccountService.getByIban("ES7921000813610123456789")).thenReturn(destinationAccount);
            doNothing().when(bankAccountService).withdrawByTransfer(any(), any(), anyString());
            doNothing().when(bankAccountService).deposit(any(), any(), any(MovementOrigin.class), anyString());

            // Act & Assert
            assertDoesNotThrow(() -> transferPaymentUseCase.execute(zeroTransferDto));
        }
    }
}
