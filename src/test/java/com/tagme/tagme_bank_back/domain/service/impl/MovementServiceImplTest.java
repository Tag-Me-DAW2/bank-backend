package com.tagme.tagme_bank_back.domain.service.impl;

import com.tagme.tagme_bank_back.domain.exception.NotFoundException;
import com.tagme.tagme_bank_back.domain.model.Movement;
import com.tagme.tagme_bank_back.domain.model.Page;
import com.tagme.tagme_bank_back.domain.repository.MovementRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovementServiceImplTest {

    @Mock
    private MovementRepository movementRepository;

    @InjectMocks
    private MovementServiceImpl movementService;

    private Movement movement;
    private Page<Movement> movementPage;

    @BeforeEach
    void setUp() {
        movement = Instancio.of(Movement.class)
                .set(field(Movement::getId), 1L)
                .create();

        movementPage = new Page<>(List.of(movement), 1, 10, 1L);
    }

    @Nested
    class GetAllTests {
        @DisplayName("Given valid page and size, when getAll is called, then return page of movements")
        @Test
        void getAllSuccess() {
            when(movementRepository.findAll(1, 10)).thenReturn(movementPage);

            Page<Movement> result = movementService.getAll(1, 10);

            assertNotNull(result);
            assertEquals(1, result.data().size());
        }

        @DisplayName("Given invalid page, when getAll is called, then throw RuntimeException")
        @Test
        void getAllInvalidPage() {
            assertThrows(RuntimeException.class, () -> movementService.getAll(0, 10));
        }

        @DisplayName("Given invalid size, when getAll is called, then throw RuntimeException")
        @Test
        void getAllInvalidSize() {
            assertThrows(RuntimeException.class, () -> movementService.getAll(1, 0));
        }
    }

    @Nested
    class GetAllByAccountIdTests {
        @DisplayName("Given valid parameters, when getAllByAccountId is called, then return page of movements")
        @Test
        void getAllByAccountIdSuccess() {
            when(movementRepository.findAllByAccountId(1L, 1, 10)).thenReturn(movementPage);

            Page<Movement> result = movementService.getAllByAccountId(1L, 1, 10);

            assertNotNull(result);
            assertEquals(1, result.data().size());
        }

        @DisplayName("Given null accountId, when getAllByAccountId is called, then throw RuntimeException")
        @Test
        void getAllByAccountIdNullId() {
            assertThrows(RuntimeException.class, () -> movementService.getAllByAccountId(null, 1, 10));
        }

        @DisplayName("Given invalid page, when getAllByAccountId is called, then throw RuntimeException")
        @Test
        void getAllByAccountIdInvalidPage() {
            assertThrows(RuntimeException.class, () -> movementService.getAllByAccountId(1L, 0, 10));
        }

        @DisplayName("Given invalid size, when getAllByAccountId is called, then throw RuntimeException")
        @Test
        void getAllByAccountIdInvalidSize() {
            assertThrows(RuntimeException.class, () -> movementService.getAllByAccountId(1L, 1, 0));
        }
    }

    @Nested
    class GetByIdTests {
        @DisplayName("Given valid id, when getById is called, then return movement")
        @Test
        void getByIdSuccess() {
            when(movementRepository.findById(1L)).thenReturn(Optional.of(movement));

            Movement result = movementService.getById(1L);

            assertNotNull(result);
            assertEquals(1L, result.getId());
        }

        @DisplayName("Given null id, when getById is called, then throw RuntimeException")
        @Test
        void getByIdNullId() {
            assertThrows(RuntimeException.class, () -> movementService.getById(null));
        }

        @DisplayName("Given non-existing id, when getById is called, then throw NotFoundException")
        @Test
        void getByIdNotFound() {
            when(movementRepository.findById(999L)).thenReturn(Optional.empty());

            assertThrows(NotFoundException.class, () -> movementService.getById(999L));
        }
    }

    @Nested
    class CreateTests {
        @DisplayName("Given valid movement and accountId, when create is called, then return saved movement")
        @Test
        void createSuccess() {
            when(movementRepository.save(any(), eq(1L))).thenReturn(movement);

            Movement result = movementService.create(movement, 1L);

            assertNotNull(result);
            verify(movementRepository).save(movement, 1L);
        }
    }

    @Nested
    class GetMonthlyMovementsTests {
        @DisplayName("Given valid parameters, when getMonthlyMovements is called, then return page of movements")
        @Test
        void getMonthlyMovementsSuccess() {
            LocalDate date = LocalDate.now();
            LocalDate startOfMonth = date.withDayOfMonth(1);
            LocalDate startOfNextMonth = startOfMonth.plusMonths(1);

            when(movementRepository.findMonthlyMovements(1L, startOfMonth, startOfNextMonth, 1, 10))
                    .thenReturn(movementPage);

            Page<Movement> result = movementService.getMonthlyMovements(1L, date, 1, 10);

            assertNotNull(result);
        }

        @DisplayName("Given null accountId, when getMonthlyMovements is called, then throw RuntimeException")
        @Test
        void getMonthlyMovementsNullAccountId() {
            assertThrows(RuntimeException.class, () -> movementService.getMonthlyMovements(null, LocalDate.now(), 1, 10));
        }

        @DisplayName("Given null date, when getMonthlyMovements is called, then throw RuntimeException")
        @Test
        void getMonthlyMovementsNullDate() {
            assertThrows(RuntimeException.class, () -> movementService.getMonthlyMovements(1L, null, 1, 10));
        }

        @DisplayName("Given invalid page, when getMonthlyMovements is called, then throw RuntimeException")
        @Test
        void getMonthlyMovementsInvalidPage() {
            assertThrows(RuntimeException.class, () -> movementService.getMonthlyMovements(1L, LocalDate.now(), 0, 10));
        }

        @DisplayName("Given invalid size, when getMonthlyMovements is called, then throw RuntimeException")
        @Test
        void getMonthlyMovementsInvalidSize() {
            assertThrows(RuntimeException.class, () -> movementService.getMonthlyMovements(1L, LocalDate.now(), 1, 0));
        }
    }

    @Nested
    class GetByCardIdTests {
        @DisplayName("Given valid parameters, when getByCardId is called, then return page of movements")
        @Test
        void getByCardIdSuccess() {
            when(movementRepository.findByCardId(1L, 1, 10)).thenReturn(movementPage);

            Page<Movement> result = movementService.getByCardId(1L, 1, 10);

            assertNotNull(result);
            assertEquals(1, result.data().size());
        }

        @DisplayName("Given null cardId, when getByCardId is called, then throw RuntimeException")
        @Test
        void getByCardIdNullId() {
            assertThrows(RuntimeException.class, () -> movementService.getByCardId(null, 1, 10));
        }

        @DisplayName("Given invalid page, when getByCardId is called, then throw RuntimeException")
        @Test
        void getByCardIdInvalidPage() {
            assertThrows(RuntimeException.class, () -> movementService.getByCardId(1L, 0, 10));
        }

        @DisplayName("Given invalid size, when getByCardId is called, then throw RuntimeException")
        @Test
        void getByCardIdInvalidSize() {
            assertThrows(RuntimeException.class, () -> movementService.getByCardId(1L, 1, 0));
        }
    }
}
