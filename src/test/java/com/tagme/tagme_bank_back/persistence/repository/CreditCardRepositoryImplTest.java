package com.tagme.tagme_bank_back.persistence.repository;

import com.tagme.tagme_bank_back.domain.model.CreditCard;
import com.tagme.tagme_bank_back.persistence.dao.jpa.CreditCardJpaDao;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.CreditCardJpaEntity;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditCardRepositoryImplTest {

    @Mock
    private CreditCardJpaDao creditCardJpaDao;

    @InjectMocks
    private CreditCardRepositoryImpl creditCardRepository;

    private CreditCardJpaEntity creditCardJpaEntity;

    @BeforeEach
    void setUp() {
        creditCardJpaEntity = new CreditCardJpaEntity();
        creditCardJpaEntity.setId(1L);
        creditCardJpaEntity.setNumber("4111111111111111");
        creditCardJpaEntity.setFullName("Test User");
        creditCardJpaEntity.setExpirationDate("12/30");
        creditCardJpaEntity.setCvv("123");
    }

    @Nested
    class FindByIdTests {
        @DisplayName("Given existing id, when findById is called, then return CreditCard")
        @Test
        void findByIdSuccess() {
            when(creditCardJpaDao.findById(1L)).thenReturn(Optional.of(creditCardJpaEntity));

            Optional<CreditCard> result = creditCardRepository.findById(1L);

            assertTrue(result.isPresent());
            assertEquals(1L, result.get().getId());
        }

        @DisplayName("Given non-existing id, when findById is called, then return empty")
        @Test
        void findByIdNotFound() {
            when(creditCardJpaDao.findById(999L)).thenReturn(Optional.empty());

            Optional<CreditCard> result = creditCardRepository.findById(999L);

            assertTrue(result.isEmpty());
        }
    }

    @Nested
    class ValidateCreditCardTests {
        @DisplayName("Given valid credit card, when validateCreditCard is called, then return true")
        @Test
        void validateCreditCardSuccess() {
            CreditCard creditCard = Instancio.of(CreditCard.class)
                    .set(field(CreditCard::getNumber), "4111111111111111")
                    .create();

            when(creditCardJpaDao.validateCreditCard(any())).thenReturn(true);

            Boolean result = creditCardRepository.validateCreditCard(creditCard);

            assertTrue(result);
        }

        @DisplayName("Given invalid credit card, when validateCreditCard is called, then return false")
        @Test
        void validateCreditCardInvalid() {
            CreditCard creditCard = Instancio.of(CreditCard.class)
                    .set(field(CreditCard::getNumber), "0000000000000000")
                    .create();

            when(creditCardJpaDao.validateCreditCard(any())).thenReturn(false);

            Boolean result = creditCardRepository.validateCreditCard(creditCard);

            assertFalse(result);
        }
    }

    @Nested
    class GetIdByCreditCardNumberTests {
        @DisplayName("Given existing card number, when getIdByCreditCardNumber is called, then return id")
        @Test
        void getIdByCreditCardNumberSuccess() {
            when(creditCardJpaDao.getIdByCreditCardNumber("4111111111111111")).thenReturn(Optional.of(1L));

            Optional<Long> result = creditCardRepository.getIdByCreditCardNumber("4111111111111111");

            assertTrue(result.isPresent());
            assertEquals(1L, result.get());
        }

        @DisplayName("Given non-existing card number, when getIdByCreditCardNumber is called, then return empty")
        @Test
        void getIdByCreditCardNumberNotFound() {
            when(creditCardJpaDao.getIdByCreditCardNumber("0000000000000000")).thenReturn(Optional.empty());

            Optional<Long> result = creditCardRepository.getIdByCreditCardNumber("0000000000000000");

            assertTrue(result.isEmpty());
        }
    }
}
