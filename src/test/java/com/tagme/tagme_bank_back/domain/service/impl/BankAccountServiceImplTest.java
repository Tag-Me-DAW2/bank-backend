package com.tagme.tagme_bank_back.domain.service.impl;

import com.tagme.tagme_bank_back.domain.exception.NotFoundException;
import com.tagme.tagme_bank_back.domain.model.*;
import com.tagme.tagme_bank_back.domain.repository.BankAccountRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceImplTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    private BankAccount bankAccount;

    @BeforeEach
    void setUp() {
        bankAccount = Instancio.of(BankAccount.class)
                .set(field(BankAccount::getId), 1L)
                .set(field(BankAccount::getIban), "ES9121000418450200051332")
                .set(field(BankAccount::getBalance), BigDecimal.valueOf(1000))
                .create();
    }

    @Nested
    class GetByIdTests {
        @DisplayName("Given valid id, when getById is called, then return bank account")
        @Test
        void getByIdSuccess() {
            when(bankAccountRepository.findById(1L)).thenReturn(Optional.of(bankAccount));

            BankAccount result = bankAccountService.getById(1L);

            assertNotNull(result);
            assertEquals(1L, result.getId());
        }

        @DisplayName("Given null id, when getById is called, then throw RuntimeException")
        @Test
        void getByIdNullId() {
            assertThrows(RuntimeException.class, () -> bankAccountService.getById(null));
        }

        @DisplayName("Given non-existing id, when getById is called, then throw NotFoundException")
        @Test
        void getByIdNotFound() {
            when(bankAccountRepository.findById(999L)).thenReturn(Optional.empty());

            assertThrows(NotFoundException.class, () -> bankAccountService.getById(999L));
        }
    }

    @Nested
    class GetByIbanTests {
        @DisplayName("Given valid iban, when getByIban is called, then return bank account")
        @Test
        void getByIbanSuccess() {
            when(bankAccountRepository.findByIban(anyString())).thenReturn(Optional.of(bankAccount));

            BankAccount result = bankAccountService.getByIban("ES9121000418450200051332");

            assertNotNull(result);
            assertEquals("ES9121000418450200051332", result.getIban());
        }

        @DisplayName("Given null iban, when getByIban is called, then throw RuntimeException")
        @Test
        void getByIbanNullIban() {
            assertThrows(RuntimeException.class, () -> bankAccountService.getByIban(null));
        }

        @DisplayName("Given blank iban, when getByIban is called, then throw RuntimeException")
        @Test
        void getByIbanBlankIban() {
            assertThrows(RuntimeException.class, () -> bankAccountService.getByIban(""));
        }

        @DisplayName("Given non-existing iban, when getByIban is called, then throw NotFoundException")
        @Test
        void getByIbanNotFound() {
            when(bankAccountRepository.findByIban(anyString())).thenReturn(Optional.empty());

            assertThrows(NotFoundException.class, () -> bankAccountService.getByIban("NONEXISTENT"));
        }
    }

    @Nested
    class CheckOwnerTests {
        @DisplayName("Given valid iban and username, when checkOwner is called, then return true")
        @Test
        void checkOwnerSuccess() {
            when(bankAccountRepository.existsByIbanAndClientUsername(anyString(), anyString())).thenReturn(true);

            Boolean result = bankAccountService.checkOwner("ES9121000418450200051332", "javier");

            assertTrue(result);
        }

        @DisplayName("Given null iban, when checkOwner is called, then throw RuntimeException")
        @Test
        void checkOwnerNullIban() {
            assertThrows(RuntimeException.class, () -> bankAccountService.checkOwner(null, "javier"));
        }

        @DisplayName("Given blank iban, when checkOwner is called, then throw RuntimeException")
        @Test
        void checkOwnerBlankIban() {
            assertThrows(RuntimeException.class, () -> bankAccountService.checkOwner("", "javier"));
        }

        @DisplayName("Given null username, when checkOwner is called, then throw RuntimeException")
        @Test
        void checkOwnerNullUsername() {
            assertThrows(RuntimeException.class, () -> bankAccountService.checkOwner("ES9121000418450200051332", null));
        }

        @DisplayName("Given blank username, when checkOwner is called, then throw RuntimeException")
        @Test
        void checkOwnerBlankUsername() {
            assertThrows(RuntimeException.class, () -> bankAccountService.checkOwner("ES9121000418450200051332", ""));
        }

        @DisplayName("Given non-matching iban and username, when checkOwner is called, then throw NotFoundException")
        @Test
        void checkOwnerNotFound() {
            when(bankAccountRepository.existsByIbanAndClientUsername(anyString(), anyString())).thenReturn(false);

            assertThrows(NotFoundException.class, () -> bankAccountService.checkOwner("INVALID", "wronguser"));
        }
    }

    @Nested
    class GetByClientIdTests {
        @DisplayName("Given valid client id, when getByClientId is called, then return accounts")
        @Test
        void getByClientIdSuccess() {
            when(bankAccountRepository.findByClientId(1L)).thenReturn(List.of(bankAccount));

            List<BankAccount> result = bankAccountService.getByClientId(1L);

            assertNotNull(result);
            assertFalse(result.isEmpty());
        }

        @DisplayName("Given null client id, when getByClientId is called, then throw RuntimeException")
        @Test
        void getByClientIdNullId() {
            assertThrows(RuntimeException.class, () -> bankAccountService.getByClientId(null));
        }
    }

    @Nested
    class GetBalanceByIbanTests {
        @DisplayName("Given valid iban, when getBalanceByIban is called, then return balance")
        @Test
        void getBalanceByIbanSuccess() {
            when(bankAccountRepository.findBalanceByIban(anyString())).thenReturn(Optional.of(BigDecimal.valueOf(1000)));

            BigDecimal result = bankAccountService.getBalanceByIban("ES9121000418450200051332");

            assertEquals(BigDecimal.valueOf(1000), result);
        }

        @DisplayName("Given null iban, when getBalanceByIban is called, then throw RuntimeException")
        @Test
        void getBalanceByIbanNullIban() {
            assertThrows(RuntimeException.class, () -> bankAccountService.getBalanceByIban(null));
        }

        @DisplayName("Given blank iban, when getBalanceByIban is called, then throw RuntimeException")
        @Test
        void getBalanceByIbanBlankIban() {
            assertThrows(RuntimeException.class, () -> bankAccountService.getBalanceByIban(""));
        }

        @DisplayName("Given non-existing iban, when getBalanceByIban is called, then throw NotFoundException")
        @Test
        void getBalanceByIbanNotFound() {
            when(bankAccountRepository.findBalanceByIban(anyString())).thenReturn(Optional.empty());

            assertThrows(NotFoundException.class, () -> bankAccountService.getBalanceByIban("NONEXISTENT"));
        }
    }

    @Nested
    class GetIbanByCreditCardNumberTests {
        @DisplayName("Given valid card number, when getIbanByCreditCardNumber is called, then return iban")
        @Test
        void getIbanByCreditCardNumberSuccess() {
            when(bankAccountRepository.findIbanByCreditCardNumber(anyString())).thenReturn(Optional.of("ES9121000418450200051332"));

            String result = bankAccountService.getIbanByCreditCardNumber("4111111111111111");

            assertEquals("ES9121000418450200051332", result);
        }

        @DisplayName("Given null card number, when getIbanByCreditCardNumber is called, then throw RuntimeException")
        @Test
        void getIbanByCreditCardNumberNullNumber() {
            assertThrows(RuntimeException.class, () -> bankAccountService.getIbanByCreditCardNumber(null));
        }

        @DisplayName("Given blank card number, when getIbanByCreditCardNumber is called, then throw RuntimeException")
        @Test
        void getIbanByCreditCardNumberBlankNumber() {
            assertThrows(RuntimeException.class, () -> bankAccountService.getIbanByCreditCardNumber(""));
        }

        @DisplayName("Given non-existing card number, when getIbanByCreditCardNumber is called, then throw NotFoundException")
        @Test
        void getIbanByCreditCardNumberNotFound() {
            when(bankAccountRepository.findIbanByCreditCardNumber(anyString())).thenReturn(Optional.empty());

            assertThrows(NotFoundException.class, () -> bankAccountService.getIbanByCreditCardNumber("0000000000000000"));
        }
    }

    @Nested
    class WithdrawByCardTests {
        @DisplayName("Given valid parameters, when withdrawByCard is called, then withdraw and save")
        @Test
        void withdrawByCardSuccess() {
            CreditCard creditCard = Instancio.of(CreditCard.class).create();
            BankAccount account = mock(BankAccount.class);

            bankAccountService.withdrawByCard(account, BigDecimal.valueOf(100), creditCard, "Test");

            verify(account).withdrawByCard(BigDecimal.valueOf(100), creditCard, "Test");
            verify(bankAccountRepository).save(account);
        }
    }

    @Nested
    class WithdrawByTransferTests {
        @DisplayName("Given valid parameters, when withdrawByTransfer is called, then withdraw and save")
        @Test
        void withdrawByTransferSuccess() {
            BankAccount account = mock(BankAccount.class);

            bankAccountService.withdrawByTransfer(account, BigDecimal.valueOf(100), "Test");

            verify(account).withdrawByTransfer(BigDecimal.valueOf(100), "Test");
            verify(bankAccountRepository).save(account);
        }
    }

    @Nested
    class DepositTests {
        @DisplayName("Given valid parameters, when deposit is called, then deposit and save")
        @Test
        void depositSuccess() {
            BankAccount account = mock(BankAccount.class);

            bankAccountService.deposit(account, BigDecimal.valueOf(100), MovementOrigin.TRANSFER, "Test");

            verify(account).deposit(BigDecimal.valueOf(100), MovementOrigin.TRANSFER, "Test");
            verify(bankAccountRepository).save(account);
        }
    }
}
