package com.tagme.tagme_bank_back.domain.service.impl;

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
    }
}