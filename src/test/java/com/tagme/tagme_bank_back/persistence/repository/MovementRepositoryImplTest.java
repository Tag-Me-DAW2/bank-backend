package com.tagme.tagme_bank_back.persistence.repository;

import com.tagme.tagme_bank_back.domain.model.Movement;
import com.tagme.tagme_bank_back.domain.model.Page;
import com.tagme.tagme_bank_back.persistence.dao.jpa.MovementJpaDao;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.MovementJpaEntity;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovementRepositoryImplTest {

    @Mock
    private MovementJpaDao movementJpaDao;

    @InjectMocks
    private MovementRepositoryImpl movementRepository;

    private MovementJpaEntity movementJpaEntity;

    @BeforeEach
    void setUp() {
        movementJpaEntity = new MovementJpaEntity();
        movementJpaEntity.setId(1L);
        movementJpaEntity.setAmount(BigDecimal.valueOf(100));
        movementJpaEntity.setDate(LocalDate.now());
        movementJpaEntity.setConcept("Test movement");
    }

    @Nested
    class FindAllTests {
        @DisplayName("Given valid page and size, when findAll is called, then return Page of movements")
        @Test
        void findAllSuccess() {
            when(movementJpaDao.findAll(1, 10)).thenReturn(List.of(movementJpaEntity));
            when(movementJpaDao.count()).thenReturn(1L);

            Page<Movement> result = movementRepository.findAll(1, 10);

            assertNotNull(result);
            assertEquals(1, result.data().size());
            assertEquals(1L, result.totalElements());
        }
    }

    @Nested
    class FindAllByAccountIdTests {
        @DisplayName("Given valid accountId, when findAllByAccountId is called, then return Page of movements")
        @Test
        void findAllByAccountIdSuccess() {
            when(movementJpaDao.findAllByAccountId(1L, 1, 10)).thenReturn(List.of(movementJpaEntity));
            when(movementJpaDao.count()).thenReturn(1L);

            Page<Movement> result = movementRepository.findAllByAccountId(1L, 1, 10);

            assertNotNull(result);
            assertEquals(1, result.data().size());
        }

        @DisplayName("Given non-existing accountId, when findAllByAccountId is called, then return empty Page")
        @Test
        void findAllByAccountIdEmpty() {
            when(movementJpaDao.findAllByAccountId(999L, 1, 10)).thenReturn(List.of());
            when(movementJpaDao.count()).thenReturn(0L);

            Page<Movement> result = movementRepository.findAllByAccountId(999L, 1, 10);

            assertNotNull(result);
            assertTrue(result.data().isEmpty());
        }
    }

    @Nested
    class FindByIdTests {
        @DisplayName("Given existing id, when findById is called, then return Movement")
        @Test
        void findByIdSuccess() {
            when(movementJpaDao.findById(1L)).thenReturn(Optional.of(movementJpaEntity));

            Optional<Movement> result = movementRepository.findById(1L);

            assertTrue(result.isPresent());
            assertEquals(1L, result.get().getId());
        }

        @DisplayName("Given non-existing id, when findById is called, then return empty")
        @Test
        void findByIdNotFound() {
            when(movementJpaDao.findById(999L)).thenReturn(Optional.empty());

            Optional<Movement> result = movementRepository.findById(999L);

            assertTrue(result.isEmpty());
        }
    }

    @Nested
    class SaveTests {
        @DisplayName("Given new movement, when save is called, then insert and return Movement")
        @Test
        void saveNewMovement() {
            Movement newMovement = Instancio.of(Movement.class)
                    .set(field(Movement::getId), null)
                    .create();

            when(movementJpaDao.insert(any(), eq(1L))).thenReturn(movementJpaEntity);

            Movement result = movementRepository.save(newMovement, 1L);

            assertNotNull(result);
            verify(movementJpaDao).insert(any(), eq(1L));
        }

        @DisplayName("Given existing movement, when save is called, then update and return Movement")
        @Test
        void saveExistingMovement() {
            Movement existingMovement = Instancio.of(Movement.class)
                    .set(field(Movement::getId), 1L)
                    .create();

            when(movementJpaDao.update(any())).thenReturn(movementJpaEntity);

            Movement result = movementRepository.save(existingMovement, 1L);

            assertNotNull(result);
            verify(movementJpaDao).update(any());
        }
    }

    @Nested
    class FindMonthlyMovementsTests {
        @DisplayName("Given valid parameters, when findMonthlyMovements is called, then return Page of movements")
        @Test
        void findMonthlyMovementsSuccess() {
            LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
            LocalDate startOfNextMonth = startOfMonth.plusMonths(1);

            when(movementJpaDao.findMonthlyMovements(1L, startOfMonth, startOfNextMonth, 1, 10))
                    .thenReturn(List.of(movementJpaEntity));
            when(movementJpaDao.count()).thenReturn(1L);

            Page<Movement> result = movementRepository.findMonthlyMovements(1L, startOfMonth, startOfNextMonth, 1, 10);

            assertNotNull(result);
            assertEquals(1, result.data().size());
        }
    }

    @Nested
    class FindByCardIdTests {
        @DisplayName("Given existing cardId, when findByCardId is called, then return Page of movements")
        @Test
        void findByCardIdSuccess() {
            when(movementJpaDao.findByCardId(1L, 1, 10)).thenReturn(List.of(movementJpaEntity));
            when(movementJpaDao.count()).thenReturn(1L);

            Page<Movement> result = movementRepository.findByCardId(1L, 1, 10);

            assertNotNull(result);
            assertEquals(1, result.data().size());
        }

        @DisplayName("Given non-existing cardId, when findByCardId is called, then return empty Page")
        @Test
        void findByCardIdEmpty() {
            when(movementJpaDao.findByCardId(999L, 1, 10)).thenReturn(List.of());
            when(movementJpaDao.count()).thenReturn(0L);

            Page<Movement> result = movementRepository.findByCardId(999L, 1, 10);

            assertNotNull(result);
            assertTrue(result.data().isEmpty());
        }
    }
}
