package com.tagme.tagme_bank_back.web.util;

import com.tagme.tagme_bank_back.domain.model.Client;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JwtUtil Tests")
class JwtUtilTest {

    @Nested
    @DisplayName("generateToken Tests")
    class GenerateTokenTests {

        @Test
        @DisplayName("Debería generar token para cliente")
        void generateToken_ValidClient_ReturnsToken() {
            Client client = Instancio.of(Client.class)
                    .set(field(Client.class, "id"), 1L)
                    .set(field(Client.class, "username"), "testuser")
                    .withSeed(10)
                    .create();

            String token = JwtUtil.generateToken(client);

            assertNotNull(token);
            assertFalse(token.isEmpty());
            assertTrue(token.split("\\.").length == 3); // JWT format: header.payload.signature
        }
    }

    @Nested
    @DisplayName("validateToken Tests")
    class ValidateTokenTests {

        @Test
        @DisplayName("Debería validar token correcto")
        void validateToken_ValidToken_ReturnsTrue() {
            Client client = Instancio.of(Client.class)
                    .set(field(Client.class, "id"), 1L)
                    .set(field(Client.class, "username"), "testuser")
                    .withSeed(10)
                    .create();

            String token = JwtUtil.generateToken(client);

            assertTrue(JwtUtil.validateToken(token));
        }

        @Test
        @DisplayName("Debería rechazar token inválido")
        void validateToken_InvalidToken_ReturnsFalse() {
            assertFalse(JwtUtil.validateToken("invalid.token.here"));
        }

        @Test
        @DisplayName("Debería rechazar token null")
        void validateToken_NullToken_ReturnsFalse() {
            assertFalse(JwtUtil.validateToken(null));
        }

        @Test
        @DisplayName("Debería rechazar token vacío")
        void validateToken_EmptyToken_ReturnsFalse() {
            assertFalse(JwtUtil.validateToken(""));
        }
    }

    @Nested
    @DisplayName("extractUsername Tests")
    class ExtractUsernameTests {

        @Test
        @DisplayName("Debería extraer username del token")
        void extractUsername_ValidToken_ReturnsUsername() {
            Client client = Instancio.of(Client.class)
                    .set(field(Client.class, "id"), 1L)
                    .set(field(Client.class, "username"), "testuser")
                    .withSeed(10)
                    .create();

            String token = JwtUtil.generateToken(client);

            String username = JwtUtil.extractUsername(token);

            assertEquals("testuser", username);
        }
    }

    @Nested
    @DisplayName("extractClientId Tests")
    class ExtractClientIdTests {

        @Test
        @DisplayName("Debería extraer clientId del token")
        void extractClientId_ValidToken_ReturnsClientId() {
            Client client = Instancio.of(Client.class)
                    .set(field(Client.class, "id"), 123L)
                    .set(field(Client.class, "username"), "testuser")
                    .withSeed(10)
                    .create();

            String token = JwtUtil.generateToken(client);

            Long clientId = JwtUtil.extractClientId(token);

            assertEquals(123L, clientId);
        }
    }

    @Nested
    @DisplayName("extractExpirationDate Tests")
    class ExtractExpirationDateTests {

        @Test
        @DisplayName("Debería extraer fecha de expiración del token")
        void extractExpirationDate_ValidToken_ReturnsExpirationDate() {
            Client client = Instancio.of(Client.class)
                    .set(field(Client.class, "id"), 1L)
                    .set(field(Client.class, "username"), "testuser")
                    .withSeed(10)
                    .create();

            String token = JwtUtil.generateToken(client);

            LocalDateTime expirationDate = JwtUtil.extractExpirationDate(token);

            assertNotNull(expirationDate);
            assertTrue(expirationDate.isAfter(LocalDateTime.now()));
        }
    }

    @Nested
    @DisplayName("getExpirationTime Tests")
    class GetExpirationTimeTests {

        @Test
        @DisplayName("Debería devolver tiempo de expiración futuro")
        void getExpirationTime_ReturnsExpirationTime() {
            LocalDateTime expirationTime = JwtUtil.getExpirationTime();

            assertNotNull(expirationTime);
            assertTrue(expirationTime.isAfter(LocalDateTime.now()));
        }
    }

    @Nested
    @DisplayName("parseClaims Tests")
    class ParseClaimsTests {

        @Test
        @DisplayName("Debería parsear claims del token")
        void parseClaims_ValidToken_ReturnsClaims() {
            Client client = Instancio.of(Client.class)
                    .set(field(Client.class, "id"), 1L)
                    .set(field(Client.class, "username"), "testuser")
                    .withSeed(10)
                    .create();

            String token = JwtUtil.generateToken(client);

            var claims = JwtUtil.parseClaims(token);

            assertNotNull(claims);
            assertEquals("testuser", claims.get("username", String.class));
            assertEquals(1L, claims.get("clientId", Long.class));
        }
    }
}
