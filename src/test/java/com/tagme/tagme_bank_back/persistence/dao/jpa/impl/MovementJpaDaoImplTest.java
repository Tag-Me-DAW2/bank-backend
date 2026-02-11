package com.tagme.tagme_bank_back.persistence.dao.jpa.impl;

import com.tagme.tagme_bank_back.annotation.DaoTest;
import com.tagme.tagme_bank_back.domain.exception.NotFoundException;
import com.tagme.tagme_bank_back.domain.model.MovementOrigin;
import com.tagme.tagme_bank_back.domain.model.MovementType;
import com.tagme.tagme_bank_back.persistence.dao.jpa.BankAccountJpaDao;
import com.tagme.tagme_bank_back.persistence.dao.jpa.MovementJpaDao;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.MovementJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DaoTest
class MovementJpaDaoImplTest extends BaseJpaDaoTest<MovementJpaDao> {

    @Autowired
    private BankAccountJpaDao bankAccountJpaDao;

    private MovementJpaEntity createMovement(BigDecimal amount, String concept) {
        MovementJpaEntity movement = new MovementJpaEntity();
        movement.setAmount(amount);
        movement.setDate(LocalDate.now());
        movement.setConcept(concept);
        movement.setOrigin(MovementOrigin.TRANSFER);
        movement.setType(MovementType.DEPOSIT);
        return movement;
    }

    @Nested
    class FindAllTests {
        @DisplayName("Given valid page and size, when findAll is called, then return movements")
        @Test
        void findAllSuccess() {
            List<MovementJpaEntity> result = dao.findAll(1, 10);
            assertNotNull(result);
        }

        @DisplayName("Given zero page, when findAll is called, then treat as page 1")
        @Test
        void findAllZeroPage() {
            List<MovementJpaEntity> result = dao.findAll(0, 10);
            assertNotNull(result);
        }
    }

    @Nested
    class FindAllByAccountIdTests {
        @DisplayName("Given existing account id, when findAllByAccountId is called, then return movements")
        @Test
        void findAllByAccountIdExisting() {
            List<MovementJpaEntity> result = dao.findAllByAccountId(1L, 1, 10);
            assertNotNull(result);
        }

        @DisplayName("Given non-existing account id, when findAllByAccountId is called, then return empty list")
        @Test
        void findAllByAccountIdNonExisting() {
            List<MovementJpaEntity> result = dao.findAllByAccountId(9999L, 1, 10);
            assertNotNull(result);
            assertTrue(result.isEmpty());
        }
    }

    @Nested
    class CountTests {
        @DisplayName("When count is called, then return number of movements")
        @Test
        void count() {
            Long result = dao.count();
            assertNotNull(result);
            assertTrue(result >= 0);
        }
    }

    @Nested
    class FindByIdTests {
        @DisplayName("Given existing id, when findById is called, then return movement")
        @Test
        void findByIdExisting() {
            MovementJpaEntity movement = createMovement(BigDecimal.valueOf(100), "Test movement");
            MovementJpaEntity inserted = dao.insert(movement, 1L);

            Optional<MovementJpaEntity> result = dao.findById(inserted.getId());
            assertTrue(result.isPresent());
        }

        @DisplayName("Given non-existing id, when findById is called, then return empty")
        @Test
        void findByIdNonExisting() {
            Optional<MovementJpaEntity> result = dao.findById(9999L);
            assertTrue(result.isEmpty());
        }
    }

    @Nested
    class InsertTests {
        @DisplayName("Given valid movement with existing bank account, when insert is called, then persist")
        @Test
        void insertSuccess() {
            MovementJpaEntity movement = createMovement(BigDecimal.valueOf(200), "New movement");

            MovementJpaEntity result = dao.insert(movement, 1L);

            assertNotNull(result.getId());
            assertEquals(BigDecimal.valueOf(200), result.getAmount());
        }

        @DisplayName("Given non-existing bank account id, when insert is called, then throw NotFoundException")
        @Test
        void insertNonExistingAccount() {
            MovementJpaEntity movement = createMovement(BigDecimal.valueOf(100), "Test");

            assertThrows(NotFoundException.class, () -> dao.insert(movement, 9999L));
        }
    }

    @Nested
    class UpdateTests {
        @DisplayName("Given existing movement, when update is called, then update movement")
        @Test
        void updateSuccess() {
            MovementJpaEntity movement = createMovement(BigDecimal.valueOf(150), "Original");
            MovementJpaEntity inserted = dao.insert(movement, 1L);

            inserted.setConcept("Updated");
            MovementJpaEntity result = dao.update(inserted);

            assertEquals("Updated", result.getConcept());
        }

        @DisplayName("Given non-existing movement, when update is called, then throw exception")
        @Test
        void updateNonExisting() {
            MovementJpaEntity movement = createMovement(BigDecimal.valueOf(100), "Test");
            movement.setId(9999L);

            assertThrows(IllegalArgumentException.class, () -> dao.update(movement));
        }
    }

    @Nested
    class DeleteByIdTests {
        @DisplayName("Given existing movement, when deleteById is called, then delete movement")
        @Test
        void deleteByIdExisting() {
            MovementJpaEntity movement = createMovement(BigDecimal.valueOf(75), "To delete");
            MovementJpaEntity inserted = dao.insert(movement, 1L);

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
    class FindMonthlyMovementsTests {
        @DisplayName("Given valid parameters, when findMonthlyMovements is called, then return movements")
        @Test
        void findMonthlyMovementsSuccess() {
            LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
            LocalDate startOfNextMonth = startOfMonth.plusMonths(1);

            List<MovementJpaEntity> result = dao.findMonthlyMovements(1L, startOfMonth, startOfNextMonth, 1, 10);
            assertNotNull(result);
        }

        @DisplayName("Given month with no movements, when findMonthlyMovements is called, then return empty list")
        @Test
        void findMonthlyMovementsEmpty() {
            LocalDate pastStart = LocalDate.of(2000, 1, 1);
            LocalDate pastEnd = LocalDate.of(2000, 2, 1);

            List<MovementJpaEntity> result = dao.findMonthlyMovements(1L, pastStart, pastEnd, 1, 10);
            assertNotNull(result);
            assertTrue(result.isEmpty());
        }
    }

    @Nested
    class FindByCardIdTests {
        @DisplayName("Given existing card id, when findByCardId is called, then return movements")
        @Test
        void findByCardIdExisting() {
            List<MovementJpaEntity> result = dao.findByCardId(1L, 1, 10);
            assertNotNull(result);
        }

        @DisplayName("Given non-existing card id, when findByCardId is called, then return empty list")
        @Test
        void findByCardIdNonExisting() {
            List<MovementJpaEntity> result = dao.findByCardId(9999L, 1, 10);
            assertNotNull(result);
            assertTrue(result.isEmpty());
        }

        @DisplayName("Given page 0, when findByCardId is called, then treat as page 1")
        @Test
        void findByCardIdWithPageZero() {
            List<MovementJpaEntity> pageZero = dao.findByCardId(1L, 0, 10);
            List<MovementJpaEntity> firstPage = dao.findByCardId(1L, 1, 10);
            assertEquals(firstPage.size(), pageZero.size());
        }
    }

}
