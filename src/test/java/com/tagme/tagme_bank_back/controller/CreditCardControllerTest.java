package com.tagme.tagme_bank_back.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tagme.tagme_bank_back.domain.exception.NotFoundException;
import com.tagme.tagme_bank_back.domain.model.CreditCard;
import com.tagme.tagme_bank_back.domain.model.Movement;
import com.tagme.tagme_bank_back.domain.model.Page;
import com.tagme.tagme_bank_back.domain.service.CreditCardService;
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

import java.util.List;

import static org.instancio.Select.field;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CreditCardController.class)
@AutoConfigureMockMvc(addFilters = false)
class CreditCardControllerTest {

    @MockitoBean
    private CreditCardService creditCardService;

    @MockitoBean
    private MovementService movementService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    class GetCreditCardByIdTests {
        @DisplayName("Given existing id, when GET /credit-cards/{id} is called, then return credit card")
        @Test
        void getCreditCardByIdSuccess() throws Exception {
            CreditCard creditCard = Instancio.of(CreditCard.class)
                    .set(field(CreditCard::getId), 1L)
                    .set(field(CreditCard::getNumber), "4111111111111111")
                    .create();

            when(creditCardService.getById(1L)).thenReturn(creditCard);

            mockMvc.perform(get("/credit-cards/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L));
        }

        @DisplayName("Given non-existing id, when GET /credit-cards/{id} is called, then return 404")
        @Test
        void getCreditCardByIdNotFound() throws Exception {
            when(creditCardService.getById(999L)).thenThrow(new NotFoundException("Credit Card Not Found"));

            mockMvc.perform(get("/credit-cards/999"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    class GetCreditCardMovementsTests {
        @DisplayName("Given existing card id, when GET /credit-cards/movements/{id} is called, then return movements")
        @Test
        void getCreditCardMovementsSuccess() throws Exception {
            Movement movement = Instancio.of(Movement.class)
                    .set(field(Movement::getId), 1L)
                    .create();

            Page<Movement> page = new Page<>(List.of(movement), 1, 10, 1L);
            when(movementService.getByCardId(1L, 1, 10)).thenReturn(page);

            mockMvc.perform(get("/credit-cards/movements/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.length()").value(1));
        }

        @DisplayName("Given custom pagination, when GET /credit-cards/movements/{id} is called, then use custom values")
        @Test
        void getCreditCardMovementsWithPagination() throws Exception {
            Page<Movement> page = new Page<>(List.of(), 2, 5, 0L);
            when(movementService.getByCardId(1L, 2, 5)).thenReturn(page);

            mockMvc.perform(get("/credit-cards/movements/1")
                            .param("page", "2")
                            .param("size", "5"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.pageNumber").value(2))
                    .andExpect(jsonPath("$.pageSize").value(5));
        }

        @DisplayName("Given invalid pagination, when GET /credit-cards/movements/{id} is called, then return 500")
        @Test
        void getCreditCardMovementsInvalidPagination() throws Exception {
            when(movementService.getByCardId(1L, 0, 10)).thenThrow(new RuntimeException("Invalid pagination"));

            mockMvc.perform(get("/credit-cards/movements/1")
                            .param("page", "0"))
                    .andExpect(status().isInternalServerError());
        }
    }
}
