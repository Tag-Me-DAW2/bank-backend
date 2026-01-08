package com.tagme.tagme_bank_back.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tagme.tagme_bank_back.controller.webModel.request.CredentialsRequest;
import com.tagme.tagme_bank_back.domain.service.ClientService;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.instancio.Select.field;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
class ClientControllerTest {
    @MockitoBean
    private ClientService clientService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    class PostTests {
        @Nested
        class CheckClientTests {
            @DisplayName("Given valid credentials, when POST /clients is called, then it should return 200 OK")
            @Test
            void givenValidCredentials_whenPostClients_thenReturn200() throws Exception{
                CredentialsRequest credentialsRequest = Instancio.of(CredentialsRequest.class).create();
                when(clientService.checkCredentials(credentialsRequest.username(), credentialsRequest.apiKey()))
                        .thenReturn(true);

                String requestBody = objectMapper.writeValueAsString(credentialsRequest);

                mockMvc.perform(post("/clients")
                                .contentType("application/json")
                                .content(requestBody))
                        .andExpect(status().isOk());
            }

            @DisplayName("Given invalid credentials, when POST /clients is called, then it should return 401 UNAUTHORIZED")
            @Test
            void givenInvalidCredentials_whenPostClients_thenReturn401() throws Exception {
                CredentialsRequest credentialsRequest = Instancio.of(CredentialsRequest.class).create();
                when(clientService.checkCredentials(credentialsRequest.username(), credentialsRequest.apiKey()))
                        .thenReturn(false);

                String requestBody = objectMapper.writeValueAsString(credentialsRequest);
                mockMvc.perform(post("/clients")
                                .contentType("application/json")
                                .content(requestBody))
                        .andExpect(status().isUnauthorized());
            }

            @DisplayName("Given missing username, when POST /clients is called, then it should return 400 BAD REQUEST")
            @Test
            void givenMissingUsername_whenPostClients_thenReturn400() throws Exception {
                CredentialsRequest credentialsRequest = Instancio.of(CredentialsRequest.class)
                        .set(field("username"), "")
                        .create();

                String requestBody = objectMapper.writeValueAsString(credentialsRequest);
                mockMvc.perform(post("/clients")
                                .contentType("application/json")
                                .content(requestBody))
                        .andExpect(status().isBadRequest());
            }
        }
    }
}