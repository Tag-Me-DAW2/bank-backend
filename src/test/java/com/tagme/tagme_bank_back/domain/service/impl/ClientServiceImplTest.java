package com.tagme.tagme_bank_back.domain.service.impl;

import com.tagme.tagme_bank_back.domain.exception.InvalidCredentialsException;
import com.tagme.tagme_bank_back.domain.exception.NotFoundException;
import com.tagme.tagme_bank_back.domain.model.Client;
import com.tagme.tagme_bank_back.domain.repository.ClientRepository;
import com.tagme.tagme_bank_back.domain.service.ClientService;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
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

    @Nested
    class getClientByUsernameTests {
        @DisplayName("Given existing username, when getClientByUsername is called, then it should return the Client")
        @Test
        void givenExistingUsername_whenGetClientByUsername_thenReturnClient() {
            String username = "existingUser";
            var client = Instancio.of(Client.class).create();

            when(clientRepository.findByUsername(anyString())).thenReturn(Optional.of(client));

            Client result = clientService.getClientByUsername(username);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(client, result)
            );
        }

        @DisplayName("Given non-existing username, when getClientByUsername is called, then should throw NotFoundException")
        @Test
        void givenNonExistingUsername_whenGetClientByUsername_thenThrowException() {
            String username = "nonExistingUser";

            when(clientRepository.findByUsername(anyString())).thenReturn(Optional.empty());
            assertThrows(NotFoundException.class, () -> {
                clientService.getClientByUsername(username);
            });
        }
    }


}