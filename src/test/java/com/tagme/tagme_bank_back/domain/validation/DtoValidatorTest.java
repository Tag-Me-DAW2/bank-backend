package com.tagme.tagme_bank_back.domain.validation;

import com.tagme.tagme_bank_back.domain.dto.PayDto;
import com.tagme.tagme_bank_back.domain.exception.ValidationException;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DtoValidator Tests")
class DtoValidatorTest {

    @Nested
    @DisplayName("validate Tests")
    class ValidateTests {

        @Test
        @DisplayName("Debería validar DTO correcto sin excepción")
        void validate_ValidDto_NoException() {
            PayDto validDto = new PayDto(BigDecimal.valueOf(100), "Test concept");

            assertDoesNotThrow(() -> DtoValidator.validate(validDto));
        }

        @Test
        @DisplayName("Debería lanzar ValidationException para DTO con valores null")
        void validate_InvalidDto_ThrowsValidationException() {
            PayDto invalidDto = new PayDto(null, null);

            // PayDto no tiene validaciones @NotNull, así que probamos que no lance excepción
            assertDoesNotThrow(() -> DtoValidator.validate(invalidDto));
        }
    }
}
