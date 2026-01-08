package com.tagme.tagme_bank_back.controller;

import com.tagme.tagme_bank_back.controller.mapper.ClientMapper;
import com.tagme.tagme_bank_back.controller.webModel.request.LoginRequest;
import com.tagme.tagme_bank_back.controller.webModel.response.ClientResponse;
import com.tagme.tagme_bank_back.controller.webModel.response.LoginResponse;
import com.tagme.tagme_bank_back.domain.exception.InvalidCredentialsException;
import com.tagme.tagme_bank_back.domain.model.Client;
import com.tagme.tagme_bank_back.domain.service.AuthService;
import com.tagme.tagme_bank_back.web.util.JwtUtil;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {
    @MockitoBean
    private AuthService authService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    class LoginTests {
        @DisplayName("Given valid credentials, when login is called, then return token")
        @Test
        void testLoginWithValidCredentials() throws Exception {
            Client client = Instancio.of(Client.class).create();
            LoginRequest loginRequest = Instancio.of(LoginRequest.class)
                    .set(field("username"), client.getUsername())
                    .set(field("password"), client.getPassword())
                    .create();

            String token = JwtUtil.generateToken(client);
            ClientResponse clientResponse = ClientMapper.fromClientToClientResponse(client);

            String requestBody = objectMapper.writeValueAsString(loginRequest);

            when(authService.authenticate(any(), any())).thenReturn(Map.of(client, token));

            mockMvc.perform(post("/auth/login")
                            .contentType("application/json")
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.token").value(token))
                    .andExpect(jsonPath("$.clientResponse.username").value(clientResponse.username()));
        }

        @DisplayName("Given invalid credentials, when login is called, then return unauthorized")
        @Test
        void testLoginWithInvalidCredentials() throws Exception {
            LoginRequest loginRequest = Instancio.of(LoginRequest.class).create();
            String requestBody = objectMapper.writeValueAsString(loginRequest);
            when(authService.authenticate(any(), any())).thenThrow(new InvalidCredentialsException("Invalid credentials"));
            mockMvc.perform(post("/auth/login")
                            .contentType("application/json")
                            .content(requestBody))
                    .andExpect(status().isUnauthorized());
        }
    }

    @Nested
    class LogoutTests {
        @DisplayName("Given a valid token, when logout is called, then no content status is returned")
        @Test
        void logoutWithValidToken() throws Exception {
            String token = "valid-token";

            doNothing().when(authServic
                    e).logout(anyString());

            mockMvc.perform(post("/auth/logout")
                            .header("Authorization", "Bearer " + token))
                    .andDo(print())
                    .andExpect(status().isNoContent());
        }

        @DisplayName("Given an invalid token, when logout is called, then an unauthorized status is returned")
        @Test
        void logoutWithInvalidToken() throws Exception {
            String token = "invalid-token";
            doThrow(new InvalidCredentialsException("Invalid token")).when(authService).logout(token);

            mockMvc.perform(post("/auth/logout")
                            .header("Authorization", "Bearer " + token))
                    .andExpect(status().isUnauthorized());
        }
    }
}