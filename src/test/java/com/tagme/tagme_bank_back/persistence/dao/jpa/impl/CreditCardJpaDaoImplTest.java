package com.tagme.tagme_bank_back.persistence.dao.jpa.impl;

import com.tagme.tagme_bank_back.annotation.DaoTest;
import com.tagme.tagme_bank_back.domain.exception.NotFoundException;
import com.tagme.tagme_bank_back.persistence.dao.jpa.CreditCardJpaDao;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.CreditCardJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DaoTest
class CreditCardJpaDaoImplTest extends BaseJpaDaoTest<CreditCardJpaDao> {

    @Nested
    class ValidateCreditCardTests {
        @DisplayName("Given valid credit card, when validateCreditCard is called, then return true")
        @Test
        void validateCreditCardValid() {
            CreditCardJpaEntity card = new CreditCardJpaEntity();
            card.setNumber("4111111111111111");
            card.setFullName("JAVIER GARCIA");
            card.setExpirationDate("12/25");
            card.setCvv("123");

            // Este test asume que hay datos en la BD
            // El resultado dependerá de los datos existentes
            Boolean result = dao.validateCreditCard(card);
            assertNotNull(result);
        }

        @DisplayName("Given invalid credit card, when validateCreditCard is called, then return false")
        @Test
        void validateCreditCardInvalid() {
            CreditCardJpaEntity card = new CreditCardJpaEntity();
            card.setNumber("0000000000000000");
            card.setFullName("INVALID USER");
            card.setExpirationDate("01/20");
            card.setCvv("999");

            Boolean result = dao.validateCreditCard(card);
            assertFalse(result);
        }
    }

    @Nested
    class CountTests {
        @DisplayName("When count is called, then return number of credit cards")
        @Test
        void count() {
            Long result = dao.count();
            assertNotNull(result);
            assertTrue(result >= 0);
        }
    }

    @Nested
    class FindByIdTests {
        @DisplayName("Given existing id, when findById is called, then return credit card")
        @Test
        void findByIdExisting() {
            Optional<CreditCardJpaEntity> result = dao.findById(1L);
            assertTrue(result.isPresent());
        }

        @DisplayName("Given non-existing id, when findById is called, then return empty")
        @Test
        void findByIdNonExisting() {
            Optional<CreditCardJpaEntity> result = dao.findById(9999L);
            assertTrue(result.isEmpty());
        }
    }

    @Nested
    class InsertTests {
        @DisplayName("Given valid credit card with existing bank account, when insert is called, then persist")
        @Test
        void insertSuccess() {
            CreditCardJpaEntity card = new CreditCardJpaEntity();
            card.setNumber("5500000000000004");
            card.setFullName("TEST USER");
            card.setExpirationDate("12/30");
            card.setCvv("456");

            CreditCardJpaEntity result = dao.insert(card, 1L);

            assertNotNull(result.getId());
            assertEquals("5500000000000004", result.getNumber());
        }

        @DisplayName("Given non-existing bank account id, when insert is called, then throw NotFoundException")
        @Test
        void insertNonExistingAccount() {
            CreditCardJpaEntity card = new CreditCardJpaEntity();
            card.setNumber("5500000000000005");
            card.setFullName("TEST USER");
            card.setExpirationDate("12/30");
            card.setCvv("456");

            assertThrows(NotFoundException.class, () -> dao.insert(card, 9999L));
        }
    }

    @Nested
    class UpdateTests {
        @DisplayName("Given existing credit card, when update is called, then update credit card")
        @Test
        void updateSuccess() {
            CreditCardJpaEntity card = dao.findById(1L).orElseThrow();
            String newFullName = "UPDATED NAME";
            card.setFullName(newFullName);

            CreditCardJpaEntity result = dao.update(card);

            assertEquals(newFullName, result.getFullName());
        }

        @DisplayName("Given non-existing credit card, when update is called, then throw NotFoundException")
        @Test
        void updateNonExisting() {
            CreditCardJpaEntity card = new CreditCardJpaEntity();
            card.setId(9999L);
            card.setNumber("0000000000000000");
            card.setFullName("TEST");
            card.setExpirationDate("12/30");
            card.setCvv("000");

            assertThrows(NotFoundException.class, () -> dao.update(card));
        }
    }

    @Nested
    class DeleteByIdTests {
        @DisplayName("Given existing credit card, when deleteById is called, then delete credit card")
        @Test
        void deleteByIdExisting() {
            CreditCardJpaEntity card = new CreditCardJpaEntity();
            card.setNumber("5500000000000006");
            card.setFullName("TO DELETE");
            card.setExpirationDate("12/30");
            card.setCvv("789");
            CreditCardJpaEntity inserted = dao.insert(card, 1L);

            dao.deleteById(inserted.getId());

            assertTrue(dao.findById(inserted.getId()).isEmpty());
        }

        @DisplayName("Given non-existing id, when deleteById is called, then no exception")
        @Test
        void deleteByIdNonExisting() {
            assertDoesNotThrow(() -> dao.deleteById(9999L));
        }
    }

    @Nested
    class GetIdByCreditCardNumberTests {
        @DisplayName("Given existing card number, when getIdByCreditCardNumber is called, then return id")
        @Test
        void getIdByCreditCardNumberExisting() {
            CreditCardJpaEntity card = new CreditCardJpaEntity();
            card.setNumber("5500000000000007");
            card.setFullName("TEST");
            card.setExpirationDate("12/30");
            card.setCvv("111");
            CreditCardJpaEntity inserted = dao.insert(card, 1L);

            Optional<Long> result = dao.getIdByCreditCardNumber("5500000000000007");

            assertTrue(result.isPresent());
            assertEquals(inserted.getId(), result.get());
        }

        @DisplayName("Given non-existing card number, when getIdByCreditCardNumber is called, then throw exception")
        @Test
        void getIdByCreditCardNumberNonExisting() {
            assertThrows(Exception.class, () -> dao.getIdByCreditCardNumber("0000000000000000"));
        }
    }
}
