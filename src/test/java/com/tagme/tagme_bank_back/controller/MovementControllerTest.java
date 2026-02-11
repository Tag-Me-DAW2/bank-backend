package com.tagme.tagme_bank_back.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tagme.tagme_bank_back.domain.exception.NotFoundException;
import com.tagme.tagme_bank_back.domain.model.Movement;
import com.tagme.tagme_bank_back.domain.model.Page;
import com.tagme.tagme_bank_back.domain.service.MovementService;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.instancio.Select.field;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovementController.class)
@AutoConfigureMockMvc(addFilters = false)
class MovementControllerTest {

    @MockitoBean
    private MovementService movementService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    class GetMovementByIdTests {
        @DisplayName("Given existing id, when GET /movements/{id} is called, then return movement")
        @Test
        void getMovementByIdSuccess() throws Exception {
            Movement movement = Instancio.of(Movement.class)
                    .set(field(Movement::getId), 1L)
                    .create();

            when(movementService.getById(1L)).thenReturn(movement);

            mockMvc.perform(get("/movements/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L));
        }

        @DisplayName("Given non-existing id, when GET /movements/{id} is called, then return 404")
        @Test
        void getMovementByIdNotFound() throws Exception {
            when(movementService.getById(999L)).thenThrow(new NotFoundException("Movement not found"));

            mockMvc.perform(get("/movements/999"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    class GetMovementsByAccountIdTests {
        @DisplayName("Given existing account id, when GET /movements/account/{accountId} is called, then return movements")
        @Test
        void getMovementsByAccountIdSuccess() throws Exception {
            Movement movement = Instancio.of(Movement.class)
                    .set(field(Movement::getId), 1L)
                    .create();

            Page<Movement> page = new Page<>(List.of(movement), 1, 10, 1L);
            when(movementService.getAllByAccountId(1L, 1, 10)).thenReturn(page);

            mockMvc.perform(get("/movements/account/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.length()").value(1));
        }

        @DisplayName("Given custom pagination, when GET /movements/account/{accountId} is called, then use custom values")
        @Test
        void getMovementsByAccountIdWithPagination() throws Exception {
            Page<Movement> page = new Page<>(List.of(), 2, 5, 0L);
            when(movementService.getAllByAccountId(1L, 2, 5)).thenReturn(page);

            mockMvc.perform(get("/movements/account/1")
                            .param("page", "2")
                            .param("size", "5"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.pageNumber").value(2))
                    .andExpect(jsonPath("$.pageSize").value(5));
        }

        @DisplayName("Given invalid pagination, when GET /movements/account/{accountId} is called, then return 500")
        @Test
        void getMovementsByAccountIdInvalidPagination() throws Exception {
            when(movementService.getAllByAccountId(1L, 0, 10)).thenThrow(new RuntimeException("Invalid pagination"));

            mockMvc.perform(get("/movements/account/1")
                            .param("page", "0"))
                    .andExpect(status().isInternalServerError());
        }
    }

    @Nested
    class GetMonthlyMovementsTests {
        @DisplayName("Given valid parameters, when GET /movements/monthly is called, then return movements")
        @Test
        void getMonthlyMovementsSuccess() throws Exception {
            Movement movement = Instancio.of(Movement.class)
                    .set(field(Movement::getId), 1L)
                    .create();

            LocalDate date = LocalDate.of(2024, 1, 15);
            Page<Movement> page = new Page<>(List.of(movement), 1, 10, 1L);
            when(movementService.getMonthlyMovements(1L, date, 1, 10)).thenReturn(page);

            mockMvc.perform(get("/movements/monthly")
                            .param("accountId", "1")
                            .param("date", "2024-01-15"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.length()").value(1));
        }

        @DisplayName("Given custom pagination, when GET /movements/monthly is called, then use custom values")
        @Test
        void getMonthlyMovementsWithPagination() throws Exception {
            LocalDate date = LocalDate.of(2024, 1, 15);
            Page<Movement> page = new Page<>(List.of(), 2, 5, 0L);
            when(movementService.getMonthlyMovements(1L, date, 2, 5)).thenReturn(page);

            mockMvc.perform(get("/movements/monthly")
                            .param("accountId", "1")
                            .param("date", "2024-01-15")
                            .param("page", "2")
                            .param("size", "5"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.pageNumber").value(2))
                    .andExpect(jsonPath("$.pageSize").value(5));
        }

        @DisplayName("Given missing accountId, when GET /movements/monthly is called, then return 500")
        @Test
        void getMonthlyMovementsMissingAccountId() throws Exception {
            mockMvc.perform(get("/movements/monthly")
                            .param("date", "2024-01-15"))
                    .andExpect(status().isInternalServerError());
        }

        @DisplayName("Given missing date, when GET /movements/monthly is called, then return 500")
        @Test
        void getMonthlyMovementsMissingDate() throws Exception {
            mockMvc.perform(get("/movements/monthly")
                            .param("accountId", "1"))
                    .andExpect(status().isInternalServerError());
        }
    }
}
