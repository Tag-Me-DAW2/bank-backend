package com.tagme.tagme_bank_back.persistence.dao.jpa.impl;

import com.tagme.tagme_bank_back.annotation.DaoTest;
import com.tagme.tagme_bank_back.domain.model.Client;
import com.tagme.tagme_bank_back.persistence.dao.jpa.ClientJpaDao;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.ClientJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DaoTest
class ClientJpaDaoImplTest extends BaseJpaDaoTest<ClientJpaDao>{
    @Nested
    class ExistsByUsernameAndApiToken {
        @DisplayName("Given valid username and api token, when existsByUsernameAndApiToken is called, then return true")
        @Test
        void givenValidUsernameAndApiToken_whenExistsByUsernameAndApiToken_thenReturnTrue() {
            String username = "javier";
            String apiKey = "gurt";

            assertTrue(dao.existsByUsernameAndApiToken(username, apiKey));
        }

        @DisplayName("Given invalid username and api token, when existsByUsernameAndApiToken is called, then return false")
        @Test
        void givenInvalidUsernameAndApiToken_whenExistsByUsernameAndApiToken_thenReturnFalse() {
            String username = "invalidUser";
            String apiKey = "invalidKey";

            assertFalse(dao.existsByUsernameAndApiToken(username, apiKey));
        }
    }

    @Nested
    class findByUsername {
        @DisplayName("Given valid username, when findByUsername is called, then return client")
        @Test
        void givenValidUsername_whenFindByUsername_thenReturnClient() {
            String username = "javier";

            Client client = dao.findByUsername(username).orElse(null);
            assertTrue(client.getUsername().equals(username));
        }

        @DisplayName("Given invalid username, when findByUsername is called, then not found Exception")
        @Test
        void givenInvalidUsername_whenFindByUsername_thenNotFoundException() {
            String username = "sdf";
            Client client = dao.findByUsername(username).orElse(null);
            assertTrue(client == null);
        }
    }

    @Nested
    class CountTests {
        @DisplayName("When count is called, then return number of clients")
        @Test
        void count() {
            Long result = dao.count();
            assertNotNull(result);
            assertTrue(result >= 0);
        }
    }

    @Nested
    class FindByIdTests {
        @DisplayName("Given existing id, when findById is called, then return client")
        @Test
        void findByIdExisting() {
            Optional<ClientJpaEntity> result = dao.findById(1L);
            assertTrue(result.isPresent());
        }

        @DisplayName("Given non-existing id, when findById is called, then return empty")
        @Test
        void findByIdNonExisting() {
            Optional<ClientJpaEntity> result = dao.findById(9999L);
            assertTrue(result.isEmpty());
        }
    }

    @Nested
    class InsertTests {
        @DisplayName("Given valid client, when insert is called, then persist client")
        @Test
        void insertSuccess() {
            ClientJpaEntity client = new ClientJpaEntity();
            client.setUsername("newuser");
            client.setPassword("password123");
            client.setName("New");
            client.setLastName1("User");
            client.setLastName2("Test");
            client.setDni("12345678X");
            client.setApiKey("new-api-key-12345");

            ClientJpaEntity result = dao.insert(client);

            assertNotNull(result.getId());
            assertEquals("newuser", result.getUsername());
        }
    }

    @Nested
    class UpdateTests {
        @DisplayName("Given existing client, when update is called, then update client")
        @Test
        void updateSuccess() {
            ClientJpaEntity client = dao.findById(1L).orElseThrow();
            String originalName = client.getName();
            client.setName("UpdatedName");

            ClientJpaEntity result = dao.update(client);

            assertEquals("UpdatedName", result.getName());
            assertNotEquals(originalName, result.getName());
        }

        @DisplayName("Given non-existing client, when update is called, then throw exception")
        @Test
        void updateNonExisting() {
            ClientJpaEntity client = new ClientJpaEntity();
            client.setId(9999L);
            client.setUsername("nonexistent");
            client.setName("Test");
            client.setLastName1("Test");
            client.setApiKey("test-key");

            assertThrows(IllegalArgumentException.class, () -> dao.update(client));
        }
    }

    @Nested
    class DeleteByIdTests {
        @DisplayName("Given existing client, when deleteById is called, then delete client")
        @Test
        void deleteByIdExisting() {
            ClientJpaEntity client = new ClientJpaEntity();
            client.setUsername("todelete");
            client.setPassword("password123");
            client.setName("To");
            client.setLastName1("Delete");
            client.setLastName2("User");
            client.setDni("87654321Y");
            client.setApiKey("delete-api-key");
            ClientJpaEntity inserted = dao.insert(client);

            dao.deleteById(inserted.getId());

            assertTrue(dao.findById(inserted.getId()).isEmpty());
        }

        @DisplayName("Given non-existing id, when deleteById is called, then no exception")
        @Test
        void deleteByIdNonExisting() {
            assertDoesNotThrow(() -> dao.deleteById(9999L));
        }
    }

    @Nested
    class ApiKeyExistsTests {
        @DisplayName("Given existing api key, when apiKeyExists is called, then return true")
        @Test
        void apiKeyExistsTrue() {
            Boolean result = dao.apiKeyExists("gurt");
            assertTrue(result);
        }

        @DisplayName("Given non-existing api key, when apiKeyExists is called, then return false")
        @Test
        void apiKeyExistsFalse() {
            Boolean result = dao.apiKeyExists("nonexistent-api-key");
            assertFalse(result);
        }
    }
}