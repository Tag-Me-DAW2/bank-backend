package com.tagme.tagme_bank_back.domain.service.impl;

import com.tagme.tagme_bank_back.domain.exception.InvalidCredentialsException;
import com.tagme.tagme_bank_back.domain.exception.NotFoundException;
import com.tagme.tagme_bank_back.domain.model.Client;
import com.tagme.tagme_bank_back.domain.repository.AuthRepository;
import com.tagme.tagme_bank_back.domain.repository.ClientRepository;
import com.tagme.tagme_bank_back.domain.util.Password4jUtil;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AuthRepository authRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    @Nested
    class AuthenticateTests {
        @DisplayName("Given valid username and password, when authenticate is called, then should return a map with client and token")
        @Test
        void givenValidUsernameAndPassword_whenAuthenticate_thenReturnMapWithClientAndToken() {
            String username = "validUser";
            String password = "validPassword";

            Client client = Instancio.of(Client.class)
                    .set(field(Client::getUsername), username)
                    .set(field(Client::getPassword), password)
                    .create();

            Client clientHashed = Instancio.of(Client.class)
                    .set(field(Client::getUsername), username)
                    .set(field(Client::getPassword), Password4jUtil.hashPassword(password))
                    .create();

            when(clientRepository.findByUsername(username)).thenReturn(Optional.of(clientHashed));
            when(authRepository.login(any(Client.class))).thenReturn(Map.of(client, "validToken"));

            Map<Client, String> result = authService.authenticate(username, password);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertTrue(result.containsKey(client)),
                    () -> assertEquals("validToken", result.get(client))
            );

        }

        @DisplayName("Given invalid username, when authenticate is called, then should throw NotFoundException")
        @Test
        void givenInvalidUsername_whenAuthenticate_thenThrowNotFoundException() {
            String username = "invalidUser";
            String password = "anyPassword";

            when(clientRepository.findByUsername(username)).thenReturn(Optional.empty());
            assertThrows(NotFoundException.class, () -> {
                authService.authenticate(username, password);
            });
        }

        @DisplayName("Given valid username and invalid password, when authenticate is called, then should throw InvalidCredentialsException")
        @Test
        void givenValidUsernameAndInvalidPassword_whenAuthenticate_thenThrowInvalidCredentialsException() {
            String username = "validUser";
            String password = "invalidPassword";

            Client clientHashed = Instancio.of(Client.class)
                    .set(field(Client::getUsername), username)
                    .set(field(Client::getPassword), Password4jUtil.hashPassword("correctPassword"))
                    .create();

            when(clientRepository.findByUsername(username)).thenReturn(Optional.of(clientHashed));
            assertThrows(InvalidCredentialsException.class, () -> {
                authService.authenticate(username, password);
            });
        }
    }

    @Nested
    class LogoutTests {
        @DisplayName("Given valid token, when logout is called, then should not throw any exception")
        @Test
        void givenValidToken_whenLogout_thenNoExceptionThrown() {
            String token = "validToken";

            assertDoesNotThrow(() -> {
                authService.logout(token);
            });
        }

        @DisplayName("Given null token, when logout is called, then should throw InvalidCredentialsException")
        @Test
        void givenNullToken_whenLogout_thenThrowInvalidCredentialsException() {
            String token = null;

            assertThrows(InvalidCredentialsException.class, () -> {
                authService.logout(token);
            });
        }

        @DisplayName("Given blank token, when logout is called, then should throw InvalidCredentialsException")
        @Test
        void givenBlankToken_whenLogout_thenThrowInvalidCredentialsException() {
            String token = "   ";

            assertThrows(InvalidCredentialsException.class, () -> {
                authService.logout(token);
            });
        }
    }
}