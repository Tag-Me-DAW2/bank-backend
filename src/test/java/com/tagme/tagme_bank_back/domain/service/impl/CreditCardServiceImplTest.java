package com.tagme.tagme_bank_back.domain.service.impl;

import com.tagme.tagme_bank_back.domain.exception.NotFoundException;
import com.tagme.tagme_bank_back.domain.model.CreditCard;
import com.tagme.tagme_bank_back.domain.repository.CreditCardRepository;
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
class CreditCardServiceImplTest {

    @Mock
    private CreditCardRepository creditCardRepository;

    @InjectMocks
    private CreditCardServiceImpl creditCardService;

    private CreditCard creditCard;

    @BeforeEach
    void setUp() {
        creditCard = Instancio.of(CreditCard.class)
                .set(field(CreditCard::getId), 1L)
                .set(field(CreditCard::getNumber), "4111111111111111")
                .create();
    }

    @Nested
    class GetByIdTests {
        @DisplayName("Given valid id, when getById is called, then return credit card")
        @Test
        void getByIdSuccess() {
            when(creditCardRepository.findById(1L)).thenReturn(Optional.of(creditCard));

            CreditCard result = creditCardService.getById(1L);

            assertNotNull(result);
            assertEquals(1L, result.getId());
        }

        @DisplayName("Given null id, when getById is called, then throw RuntimeException")
        @Test
        void getByIdNullId() {
            assertThrows(RuntimeException.class, () -> creditCardService.getById(null));
        }

        @DisplayName("Given non-existing id, when getById is called, then throw NotFoundException")
        @Test
        void getByIdNotFound() {
            when(creditCardRepository.findById(999L)).thenReturn(Optional.empty());

            assertThrows(NotFoundException.class, () -> creditCardService.getById(999L));
        }
    }

    @Nested
    class ValidateCreditCardTests {
        @DisplayName("Given valid credit card, when validateCreditCard is called, then return true")
        @Test
        void validateCreditCardSuccess() {
            when(creditCardRepository.validateCreditCard(creditCard)).thenReturn(true);

            Boolean result = creditCardService.validateCreditCard(creditCard);

            assertTrue(result);
        }

        @DisplayName("Given invalid credit card, when validateCreditCard is called, then throw NotFoundException")
        @Test
        void validateCreditCardInvalid() {
            when(creditCardRepository.validateCreditCard(creditCard)).thenReturn(false);

            assertThrows(NotFoundException.class, () -> creditCardService.validateCreditCard(creditCard));
        }
    }

    @Nested
    class GetIdByCreditCardNumberTests {
        @DisplayName("Given valid card number, when getIdByCreditCardNumber is called, then return id")
        @Test
        void getIdByCreditCardNumberSuccess() {
            when(creditCardRepository.getIdByCreditCardNumber("4111111111111111")).thenReturn(Optional.of(1L));

            Long result = creditCardService.getIdByCreditCardNumber("4111111111111111");

            assertEquals(1L, result);
        }

        @DisplayName("Given non-existing card number, when getIdByCreditCardNumber is called, then throw NotFoundException")
        @Test
        void getIdByCreditCardNumberNotFound() {
            when(creditCardRepository.getIdByCreditCardNumber("0000000000000000")).thenReturn(Optional.empty());

            assertThrows(NotFoundException.class, () -> creditCardService.getIdByCreditCardNumber("0000000000000000"));
        }
    }
}
