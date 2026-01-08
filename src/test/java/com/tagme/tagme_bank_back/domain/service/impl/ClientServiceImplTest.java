package com.tagme.tagme_bank_back.domain.service.impl;

import com.tagme.tagme_bank_back.domain.exception.InvalidCredentialsException;
import com.tagme.tagme_bank_back.domain.repository.ClientRepository;
import com.tagme.tagme_bank_back.domain.service.ClientService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {
    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Nested
    class CredentialsTests {
        @DisplayName("Given valid credentials, when checkCredentials is called, then it should return true")
        @Test
        void givenValidCredentials_whenCheckCredentials_thenReturnTrue() {
            String username = "validUser";
            String apiKey = "validKey";

            when(clientRepository.existsByUsernameAndApiToken(username, apiKey)).thenReturn(Optional.of(true));

            assertTrue(clientService.checkCredentials(username, apiKey));
        }

        @DisplayName("Given invalid credentials, when checkCredentials is called, then should throw InvalidCredentialsException")
        @Test
        void givenInvalidCredentials_whenCheckCredentials_thenThrowException() {
            String username = "invalidUser";
            String apiKey = "invalidKey";

            when(clientRepository.existsByUsernameAndApiToken(username, apiKey)).thenReturn(Optional.empty());

            assertThrows(InvalidCredentialsException.class, () -> {
                clientService.checkCredentials(username, apiKey);
            });
        }

        @DisplayName("Given null credentials, when checkCredentials is called, then should throw InvalidCredentialsException")
        @Test
        void givenNullCredentials_whenCheckCredentials_thenThrowException() {
            String username = null;
            String apiKey = null;

            when(clientRepository.existsByUsernameAndApiToken(username, apiKey)).thenReturn(Optional.empty());

            assertThrows(InvalidCredentialsException.class, () -> {
                clientService.checkCredentials(username, apiKey);
            });
        }
    }
}