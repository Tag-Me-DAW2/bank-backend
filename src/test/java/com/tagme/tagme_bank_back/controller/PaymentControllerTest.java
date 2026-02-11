package com.tagme.tagme_bank_back.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tagme.tagme_bank_back.controller.webModel.request.*;
import com.tagme.tagme_bank_back.domain.exception.NotFoundException;
import com.tagme.tagme_bank_back.usecase.CreditCardPaymentUseCase;
import com.tagme.tagme_bank_back.usecase.TransferPaymentUseCase;
import com.tagme.tagme_bank_back.web.util.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
@AutoConfigureMockMvc(addFilters = false)
class PaymentControllerTest {

    @MockitoBean
    private CreditCardPaymentUseCase creditCardPaymentUseCase;

    @MockitoBean
    private TransferPaymentUseCase transferPaymentUseCase;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    class ProcessCreditCardPaymentTests {
        @DisplayName("Given valid request, when POST /payments/credit-card is called, then return 200")
        @Test
        void processCreditCardPaymentSuccess() throws Exception {
            CreditCardPaymentRequest request = new CreditCardPaymentRequest(
                    new AuthRequest("testuser", "api-key-123"),
                    new CreditCardRequest("4111111111111111", "12/25", "123", "Test User"),
                    new TransferRequest("ES9121000418450200051332"),
                    new PayRequest(BigDecimal.valueOf(100), "Test payment")
            );

            doNothing().when(creditCardPaymentUseCase).execute(any());

            mockMvc.perform(post("/payments/credit-card")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk());
        }

        @DisplayName("Given invalid credit card, when POST /payments/credit-card is called, then return 404")
        @Test
        void processCreditCardPaymentInvalidCard() throws Exception {
            CreditCardPaymentRequest request = new CreditCardPaymentRequest(
                    new AuthRequest("testuser", "api-key-123"),
                    new CreditCardRequest("0000000000000000", "01/20", "999", "Invalid User"),
                    new TransferRequest("ES9121000418450200051332"),
                    new PayRequest(BigDecimal.valueOf(100), "Test payment")
            );

            doThrow(new NotFoundException("Credit Card Not Found")).when(creditCardPaymentUseCase).execute(any());

            mockMvc.perform(post("/payments/credit-card")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    class ProcessTransferPaymentTests {
        // IBAN válido español para tests
        private static final String VALID_IBAN_1 = "ES9121000418450200051332";
        private static final String VALID_IBAN_2 = "ES7921000813610123456789";

        @DisplayName("Given valid request with valid token, when POST /payments/transfer is called, then return 200")
        @Test
        void processTransferPaymentSuccess() throws Exception {
            TransferPaymentRequest request = new TransferPaymentRequest(
                    new AuthRequest("testuser", "api-key-123"),
                    new TransferRequest(VALID_IBAN_1),
                    new TransferRequest(VALID_IBAN_2),
                    new PayRequest(BigDecimal.valueOf(100), "Test transfer")
            );

            try (MockedStatic<JwtUtil> mockedJwtUtil = mockStatic(JwtUtil.class)) {
                mockedJwtUtil.when(() -> JwtUtil.validateToken(anyString())).thenReturn(true);
                doNothing().when(transferPaymentUseCase).execute(any());

                mockMvc.perform(post("/payments/transfer")
                                .header("Authorization", "Bearer valid-token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                        .andExpect(status().isOk());
            }
        }

        @DisplayName("Given invalid token, when POST /payments/transfer is called, then return 401")
        @Test
        void processTransferPaymentInvalidToken() throws Exception {
            TransferPaymentRequest request = new TransferPaymentRequest(
                    new AuthRequest("testuser", "api-key-123"),
                    new TransferRequest(VALID_IBAN_1),
                    new TransferRequest(VALID_IBAN_2),
                    new PayRequest(BigDecimal.valueOf(100), "Test transfer")
            );

            try (MockedStatic<JwtUtil> mockedJwtUtil = mockStatic(JwtUtil.class)) {
                mockedJwtUtil.when(() -> JwtUtil.validateToken(anyString())).thenReturn(false);

                mockMvc.perform(post("/payments/transfer")
                                .header("Authorization", "Bearer invalid-token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                        .andExpect(status().isUnauthorized());
            }
        }

        @DisplayName("Given non-existing account, when POST /payments/transfer is called, then return 404")
        @Test
        void processTransferPaymentAccountNotFound() throws Exception {
            TransferPaymentRequest request = new TransferPaymentRequest(
                    new AuthRequest("testuser", "api-key-123"),
                    new TransferRequest(VALID_IBAN_1),
                    new TransferRequest(VALID_IBAN_2),
                    new PayRequest(BigDecimal.valueOf(100), "Test transfer")
            );

            try (MockedStatic<JwtUtil> mockedJwtUtil = mockStatic(JwtUtil.class)) {
                mockedJwtUtil.when(() -> JwtUtil.validateToken(anyString())).thenReturn(true);
                doThrow(new NotFoundException("Bank account not found")).when(transferPaymentUseCase).execute(any());

                mockMvc.perform(post("/payments/transfer")
                                .header("Authorization", "Bearer valid-token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                        .andExpect(status().isNotFound());
            }
        }
    }
}
