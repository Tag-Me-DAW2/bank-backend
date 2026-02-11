package com.tagme.tagme_bank_back.domain.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("IbanValidator Tests")
class IbanValidatorTest {

    private IbanValidator ibanValidator;

    @BeforeEach
    void setUp() {
        ibanValidator = new IbanValidator();
        ibanValidator.initialize(null);
    }

    @Nested
    @DisplayName("isValid Tests")
    class IsValidTests {

        @Test
        @DisplayName("Debería validar IBAN español correcto")
        void isValid_ValidSpanishIban_ReturnsTrue() {
            // IBAN español de prueba válido
            assertTrue(ibanValidator.isValid("ES9121000418450200051332", null));
        }

        @ParameterizedTest
        @NullSource
        @DisplayName("Debería rechazar IBAN null")
        void isValid_NullIban_ReturnsFalse(String iban) {
            assertFalse(ibanValidator.isValid(iban, null));
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "DE89370400440532013000",  // IBAN alemán
                "FR7630006000011234567890189",  // IBAN francés
                "GB82WEST12345698765432"  // IBAN británico
        })
        @DisplayName("Debería rechazar IBAN no español")
        void isValid_NonSpanishIban_ReturnsFalse(String iban) {
            assertFalse(ibanValidator.isValid(iban, null));
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "ES00000000000000000000",  // IBAN con dígitos de control inválidos
                "ES",  // IBAN muy corto
                "ES123",  // IBAN muy corto
                "ESABCDEFGHIJKLMNOPQRSTUV"  // IBAN con caracteres inválidos
        })
        @DisplayName("Debería rechazar IBAN español inválido")
        void isValid_InvalidSpanishIban_ReturnsFalse(String iban) {
            assertFalse(ibanValidator.isValid(iban, null));
        }

        @Test
        @DisplayName("Debería rechazar string vacío")
        void isValid_EmptyIban_ReturnsFalse() {
            assertFalse(ibanValidator.isValid("", null));
        }
    }
}
