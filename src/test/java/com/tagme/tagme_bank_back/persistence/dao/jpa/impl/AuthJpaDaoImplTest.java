package com.tagme.tagme_bank_back.persistence.dao.jpa.impl;

import com.tagme.tagme_bank_back.annotation.DaoTest;
import com.tagme.tagme_bank_back.domain.exception.NotFoundException;
import com.tagme.tagme_bank_back.domain.model.Client;
import com.tagme.tagme_bank_back.persistence.dao.jpa.AuthJpaDao;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;

@DaoTest
class AuthJpaDaoImplTest extends BaseJpaDaoTest<AuthJpaDao> {
    @Nested
    class LoginTests {
        @DisplayName("Given valid username and password, when login is called, then Client and token")
        @Test
        void givenValidUsernameAndPassword_whenLoginIsCalled_thenClientAndToken() {
            Client client = Instancio.of(Client.class)
                    .set(field(Client::getId), 1L)
                    .create();

            Map<Client, String> result = dao.login(client);
            assertAll(
                    () -> assertNotNull(result),
                    () -> assertTrue(result.containsKey(client)),
                    () -> assertNotNull(result.get(client))
            );
        }

        @DisplayName("Given invalid username or password, when login is called, then throw exception")
        @Test
        void givenInvalidUsernameOrPassword_whenLoginIsCalled_thenThrowException() {
            Client client = Instancio.of(Client.class)
                    .set(field(Client::getId), -1L)
                    .create();
            assertThrows(NotFoundException.class, () -> dao.login(client));
        }
    }

    @Nested
    class LogoutTests {
        @DisplayName("Given valid token, when logout is called, then succeed")
        @Test
        void givenValidToken_whenLogoutIsCalled_thenSucceed() {
            String token = "gurt";
            Long totalSessionsBefore = dao.count();
            dao.logout(token);
            Long totalSessionsAfter = dao.count();
            assertEquals(totalSessionsBefore - 1, totalSessionsAfter);
        }
    }
}