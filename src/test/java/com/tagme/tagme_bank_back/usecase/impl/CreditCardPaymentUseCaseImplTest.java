package com.tagme.tagme_bank_back.usecase.impl;

import com.tagme.tagme_bank_back.domain.dto.AuthDto;
import com.tagme.tagme_bank_back.domain.dto.CreditCardPaymentDto;
import com.tagme.tagme_bank_back.domain.dto.PayDto;
import com.tagme.tagme_bank_back.domain.dto.TransferDto;
import com.tagme.tagme_bank_back.domain.exception.InsufficientBalanceException;
import com.tagme.tagme_bank_back.domain.model.BankAccount;
import com.tagme.tagme_bank_back.domain.model.CreditCard;
import com.tagme.tagme_bank_back.domain.model.MovementOrigin;
import com.tagme.tagme_bank_back.domain.service.AuthService;
import com.tagme.tagme_bank_back.domain.service.BankAccountService;
import com.tagme.tagme_bank_back.domain.service.CreditCardService;
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
@DisplayName("CreditCardPaymentUseCase Tests")
class CreditCardPaymentUseCaseImplTest {

    @Mock
    private AuthService authService;

    @Mock
    private BankAccountService bankAccountService;

    @Mock
    private CreditCardService creditCardService;

    @InjectMocks
    private CreditCardPaymentUseCaseImpl creditCardPaymentUseCase;

    private CreditCard creditCard;
    private BankAccount originAccount;
    private BankAccount destinationAccount;
    private CreditCardPaymentDto paymentDto;

    @BeforeEach
    void setUp() {
        creditCard = new CreditCard(1L, "4111111111111111", "12/25", "123", "Test User");
        
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

        paymentDto = new CreditCardPaymentDto(
                new AuthDto("testuser", "api-key-123"),
                creditCard,
                new TransferDto("ES7921000813610123456789"),
                new PayDto(BigDecimal.valueOf(100), "Test payment")
        );
    }

    @Nested
    @DisplayName("execute Tests")
    class ExecuteTests {

        @Test
        @DisplayName("Given valid payment data, when execute is called, then payment is processed successfully")
        void executeSuccess() {
            // Arrange
            when(creditCardService.validateCreditCard(any(CreditCard.class))).thenReturn(true);
            when(authService.authorize(anyString(), anyString())).thenReturn(true);
            when(bankAccountService.checkOwner(anyString(), anyString())).thenReturn(true);
            when(bankAccountService.getIbanByCreditCardNumber(anyString())).thenReturn("ES9121000418450200051335");
            when(bankAccountService.getBalanceByIban(anyString())).thenReturn(BigDecimal.valueOf(1000));
            when(creditCardService.getIdByCreditCardNumber(anyString())).thenReturn(1L);
            when(bankAccountService.getByIban("ES9121000418450200051335")).thenReturn(originAccount);
            when(bankAccountService.getByIban("ES7921000813610123456789")).thenReturn(destinationAccount);
            doNothing().when(bankAccountService).withdrawByCard(any(), any(), any(), anyString());
            doNothing().when(bankAccountService).deposit(any(), any(), any(MovementOrigin.class), anyString());

            // Act & Assert
            assertDoesNotThrow(() -> creditCardPaymentUseCase.execute(paymentDto));

            verify(creditCardService).validateCreditCard(creditCard);
            verify(authService).authorize("testuser", "api-key-123");
            verify(bankAccountService).checkOwner("ES7921000813610123456789", "testuser");
            verify(bankAccountService).withdrawByCard(eq(originAccount), eq(BigDecimal.valueOf(100)), any(CreditCard.class), eq("Test payment"));
            verify(bankAccountService).deposit(eq(destinationAccount), eq(BigDecimal.valueOf(100)), eq(MovementOrigin.CARD), eq("Test payment"));
        }

        @Test
        @DisplayName("Given same origin and destination accounts, when execute is called, then throw IllegalArgumentException")
        void executeSameOriginAndDestination() {
            // Arrange - origin iban equals destination iban
            CreditCardPaymentDto sameAccountDto = new CreditCardPaymentDto(
                    new AuthDto("testuser", "api-key-123"),
                    creditCard,
                    new TransferDto("ES9121000418450200051335"),  // Same as origin
                    new PayDto(BigDecimal.valueOf(100), "Test payment")
            );

            when(creditCardService.validateCreditCard(any(CreditCard.class))).thenReturn(true);
            when(authService.authorize(anyString(), anyString())).thenReturn(true);
            when(bankAccountService.checkOwner(anyString(), anyString())).thenReturn(true);
            when(bankAccountService.getIbanByCreditCardNumber(anyString())).thenReturn("ES9121000418450200051335");

            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> creditCardPaymentUseCase.execute(sameAccountDto)
            );

            assertEquals("The origin and destination accounts cannot be the same", exception.getMessage());
        }

        @Test
        @DisplayName("Given insufficient balance, when execute is called, then throw InsufficientBalanceException")
        void executeInsufficientBalance() {
            // Arrange
            CreditCardPaymentDto largePaymentDto = new CreditCardPaymentDto(
                    new AuthDto("testuser", "api-key-123"),
                    creditCard,
                    new TransferDto("ES7921000813610123456789"),
                    new PayDto(BigDecimal.valueOf(5000), "Large payment")  // More than balance
            );

            when(creditCardService.validateCreditCard(any(CreditCard.class))).thenReturn(true);
            when(authService.authorize(anyString(), anyString())).thenReturn(true);
            when(bankAccountService.checkOwner(anyString(), anyString())).thenReturn(true);
            when(bankAccountService.getIbanByCreditCardNumber(anyString())).thenReturn("ES9121000418450200051335");
            when(bankAccountService.getBalanceByIban(anyString())).thenReturn(BigDecimal.valueOf(1000));  // Only 1000

            // Act & Assert
            InsufficientBalanceException exception = assertThrows(
                    InsufficientBalanceException.class,
                    () -> creditCardPaymentUseCase.execute(largePaymentDto)
            );

            assertEquals("Insufficient balance", exception.getMessage());
        }

        @Test
        @DisplayName("Given invalid credit card, when execute is called, then validation fails")
        void executeInvalidCreditCard() {
            // Arrange
            when(creditCardService.validateCreditCard(any(CreditCard.class)))
                    .thenThrow(new RuntimeException("Invalid credit card"));

            // Act & Assert
            assertThrows(RuntimeException.class, () -> creditCardPaymentUseCase.execute(paymentDto));

            verify(creditCardService).validateCreditCard(creditCard);
            verifyNoInteractions(authService);
        }

        @Test
        @DisplayName("Given invalid credentials, when execute is called, then authorization fails")
        void executeInvalidCredentials() {
            // Arrange
            when(creditCardService.validateCreditCard(any(CreditCard.class))).thenReturn(true);
            when(authService.authorize(anyString(), anyString()))
                    .thenThrow(new RuntimeException("Invalid credentials"));

            // Act & Assert
            assertThrows(RuntimeException.class, () -> creditCardPaymentUseCase.execute(paymentDto));

            verify(creditCardService).validateCreditCard(creditCard);
            verify(authService).authorize("testuser", "api-key-123");
        }
    }
}
