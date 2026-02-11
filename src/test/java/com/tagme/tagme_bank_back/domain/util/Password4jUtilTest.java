package com.tagme.tagme_bank_back.domain.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Password4jUtil Tests")
class Password4jUtilTest {

    @Nested
    @DisplayName("hashPassword Tests")
    class HashPasswordTests {
        @Test
        @DisplayName("Debería generar hash para password")
        void hashPassword_ShouldGenerateHash() {
            String password = "mySecurePassword123";

            String hash = Password4jUtil.hashPassword(password);

            assertNotNull(hash);
            assertNotEquals(password, hash);
            assertTrue(hash.length() > 0);
        }

        @Test
        @DisplayName("Debería generar hashes diferentes para el mismo password")
        void hashPassword_SamePassword_DifferentHashes() {
            String password = "mySecurePassword123";

            String hash1 = Password4jUtil.hashPassword(password);
            String hash2 = Password4jUtil.hashPassword(password);

            assertNotEquals(hash1, hash2);
        }
    }

    @Nested
    @DisplayName("verifyPassword Tests")
    class VerifyPasswordTests {
        @Test
        @DisplayName("Debería verificar password correcto")
        void verifyPassword_CorrectPassword_ReturnsTrue() {
            String password = "mySecurePassword123";
            String hash = Password4jUtil.hashPassword(password);

            boolean result = Password4jUtil.verifyPassword(password, hash);

            assertTrue(result);
        }

        @Test
        @DisplayName("Debería rechazar password incorrecto")
        void verifyPassword_IncorrectPassword_ReturnsFalse() {
            String password = "mySecurePassword123";
            String wrongPassword = "wrongPassword";
            String hash = Password4jUtil.hashPassword(password);

            boolean result = Password4jUtil.verifyPassword(wrongPassword, hash);

            assertFalse(result);
        }
    }
}
