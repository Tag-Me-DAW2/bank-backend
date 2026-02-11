package com.tagme.tagme_bank_back.persistence.repository;

import com.tagme.tagme_bank_back.domain.model.BankAccount;
import com.tagme.tagme_bank_back.persistence.dao.jpa.BankAccountJpaDao;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.BankAccountJpaEntity;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.ClientJpaEntity;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankAccountRepositoryImplTest {

    @Mock
    private BankAccountJpaDao bankAccountJpaDao;

    @InjectMocks
    private BankAccountRepositoryImpl bankAccountRepository;

    private BankAccountJpaEntity bankAccountJpaEntity;

    @BeforeEach
    void setUp() {
        ClientJpaEntity clientJpaEntity = Instancio.of(ClientJpaEntity.class)
                .set(field(ClientJpaEntity::getId), 1L)
                .create();

        bankAccountJpaEntity = new BankAccountJpaEntity();
        bankAccountJpaEntity.setId(1L);
        bankAccountJpaEntity.setIban("ES9121000418450200051332");
        bankAccountJpaEntity.setBalance(BigDecimal.valueOf(1000));
        bankAccountJpaEntity.setClient(clientJpaEntity);
        bankAccountJpaEntity.setMovements(new ArrayList<>());
        bankAccountJpaEntity.setCreditCards(new ArrayList<>());
    }

    @Nested
    class FindByIdTests {
        @DisplayName("Given existing id, when findById is called, then return BankAccount")
        @Test
        void findByIdSuccess() {
            when(bankAccountJpaDao.findById(1L)).thenReturn(Optional.of(bankAccountJpaEntity));

            Optional<BankAccount> result = bankAccountRepository.findById(1L);

            assertTrue(result.isPresent());
            assertEquals(1L, result.get().getId());
        }

        @DisplayName("Given non-existing id, when findById is called, then return empty")
        @Test
        void findByIdNotFound() {
            when(bankAccountJpaDao.findById(999L)).thenReturn(Optional.empty());

            Optional<BankAccount> result = bankAccountRepository.findById(999L);

            assertTrue(result.isEmpty());
        }
    }

    @Nested
    class FindByIbanTests {
        @DisplayName("Given existing iban, when findByIban is called, then return BankAccount")
        @Test
        void findByIbanSuccess() {
            when(bankAccountJpaDao.findByIban("ES9121000418450200051332")).thenReturn(Optional.of(bankAccountJpaEntity));

            Optional<BankAccount> result = bankAccountRepository.findByIban("ES9121000418450200051332");

            assertTrue(result.isPresent());
            assertEquals("ES9121000418450200051332", result.get().getIban());
        }

        @DisplayName("Given non-existing iban, when findByIban is called, then return empty")
        @Test
        void findByIbanNotFound() {
            when(bankAccountJpaDao.findByIban("NONEXISTENT")).thenReturn(Optional.empty());

            Optional<BankAccount> result = bankAccountRepository.findByIban("NONEXISTENT");

            assertTrue(result.isEmpty());
        }
    }

    @Nested
    class ExistsByIbanAndClientUsernameTests {
        @DisplayName("Given matching iban and username, when existsByIbanAndClientUsername is called, then return true")
        @Test
        void existsByIbanAndClientUsernameTrue() {
            when(bankAccountJpaDao.existsByIbanAndClientUsername("ES9121000418450200051332", "javier")).thenReturn(true);

            Boolean result = bankAccountRepository.existsByIbanAndClientUsername("ES9121000418450200051332", "javier");

            assertTrue(result);
        }

        @DisplayName("Given non-matching iban and username, when existsByIbanAndClientUsername is called, then return false")
        @Test
        void existsByIbanAndClientUsernameFalse() {
            when(bankAccountJpaDao.existsByIbanAndClientUsername("INVALID", "wronguser")).thenReturn(false);

            Boolean result = bankAccountRepository.existsByIbanAndClientUsername("INVALID", "wronguser");

            assertFalse(result);
        }
    }

    @Nested
    class FindByClientIdTests {
        @DisplayName("Given existing clientId, when findByClientId is called, then return list of BankAccounts")
        @Test
        void findByClientIdSuccess() {
            when(bankAccountJpaDao.findByClientId(1L)).thenReturn(List.of(bankAccountJpaEntity));

            List<BankAccount> result = bankAccountRepository.findByClientId(1L);

            assertNotNull(result);
            assertEquals(1, result.size());
        }

        @DisplayName("Given non-existing clientId, when findByClientId is called, then return empty list")
        @Test
        void findByClientIdEmpty() {
            when(bankAccountJpaDao.findByClientId(999L)).thenReturn(List.of());

            List<BankAccount> result = bankAccountRepository.findByClientId(999L);

            assertNotNull(result);
            assertTrue(result.isEmpty());
        }
    }

    @Nested
    class FindBalanceByIbanTests {
        @DisplayName("Given existing iban, when findBalanceByIban is called, then return balance")
        @Test
        void findBalanceByIbanSuccess() {
            when(bankAccountJpaDao.findBalanceByIban("ES9121000418450200051332")).thenReturn(Optional.of(BigDecimal.valueOf(1000)));

            Optional<BigDecimal> result = bankAccountRepository.findBalanceByIban("ES9121000418450200051332");

            assertTrue(result.isPresent());
            assertEquals(BigDecimal.valueOf(1000), result.get());
        }

        @DisplayName("Given non-existing iban, when findBalanceByIban is called, then return empty")
        @Test
        void findBalanceByIbanNotFound() {
            when(bankAccountJpaDao.findBalanceByIban("NONEXISTENT")).thenReturn(Optional.empty());

            Optional<BigDecimal> result = bankAccountRepository.findBalanceByIban("NONEXISTENT");

            assertTrue(result.isEmpty());
        }
    }

    @Nested
    class SaveTests {
        @DisplayName("Given new BankAccount, when save is called, then insert and return BankAccount")
        @Test
        void saveNewAccount() {
            BankAccount newAccount = Instancio.of(BankAccount.class)
                    .set(field(BankAccount::getId), null)
                    .set(field(BankAccount::getMovements), new ArrayList<>())
                    .create();

            when(bankAccountJpaDao.insert(any())).thenReturn(bankAccountJpaEntity);

            BankAccount result = bankAccountRepository.save(newAccount);

            assertNotNull(result);
            verify(bankAccountJpaDao).insert(any());
        }

        @DisplayName("Given existing BankAccount, when save is called, then update and return BankAccount")
        @Test
        void saveExistingAccount() {
            BankAccount existingAccount = Instancio.of(BankAccount.class)
                    .set(field(BankAccount::getId), 1L)
                    .set(field(BankAccount::getMovements), new ArrayList<>())
                    .create();

            when(bankAccountJpaDao.update(any())).thenReturn(bankAccountJpaEntity);

            BankAccount result = bankAccountRepository.save(existingAccount);

            assertNotNull(result);
            verify(bankAccountJpaDao).update(any());
        }
    }

    @Nested
    class FindIbanByCreditCardNumberTests {
        @DisplayName("Given existing card number, when findIbanByCreditCardNumber is called, then return iban")
        @Test
        void findIbanByCreditCardNumberSuccess() {
            when(bankAccountJpaDao.findIbanByCreditCardNumber("4111111111111111")).thenReturn(Optional.of("ES9121000418450200051332"));

            Optional<String> result = bankAccountRepository.findIbanByCreditCardNumber("4111111111111111");

            assertTrue(result.isPresent());
            assertEquals("ES9121000418450200051332", result.get());
        }

        @DisplayName("Given non-existing card number, when findIbanByCreditCardNumber is called, then return empty")
        @Test
        void findIbanByCreditCardNumberNotFound() {
            when(bankAccountJpaDao.findIbanByCreditCardNumber("0000000000000000")).thenReturn(Optional.empty());

            Optional<String> result = bankAccountRepository.findIbanByCreditCardNumber("0000000000000000");

            assertTrue(result.isEmpty());
        }
    }
}
