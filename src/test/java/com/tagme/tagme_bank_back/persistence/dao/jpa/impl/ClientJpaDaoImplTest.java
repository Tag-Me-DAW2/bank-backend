package com.tagme.tagme_bank_back.persistence.dao.jpa.impl;

import com.tagme.tagme_bank_back.annotation.DaoTest;
import com.tagme.tagme_bank_back.persistence.dao.jpa.ClientJpaDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DaoTest
class ClientJpaDaoImplTest extends BaseJpaDaoTest<ClientJpaDao>{
    @Nested
    class ExistsByUsernameAndApiToken {
        @DisplayName("Given valid username and api token, when existsByUsernameAndApiToken is called, then return true")
        @Test
        void givenValidUsernameAndApiToken_whenExistsByUsernameAndApiToken_thenReturnTrue() {
            String username = "javier";
            String apiKey = "gurt";

            assertTrue(dao.existsByUsernameAndApiToken(username, apiKey).orElse(false));
        }

        @DisplayName("Given invalid username and api token, when existsByUsernameAndApiToken is called, then return false")
        @Test
        void givenInvalidUsernameAndApiToken_whenExistsByUsernameAndApiToken_thenReturnFalse() {
            String username = "invalidUser";
            String apiKey = "invalidKey";

            assertFalse(dao.existsByUsernameAndApiToken(username, apiKey).orElse(false));
        }
    }
}