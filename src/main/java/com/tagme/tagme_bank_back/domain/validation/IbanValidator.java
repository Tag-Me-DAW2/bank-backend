package com.tagme.tagme_bank_back.domain.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.validator.routines.IBANValidator;

public class IbanValidator implements ConstraintValidator<ValidIban, String> {

    private IBANValidator ibanValidator;

    @Override
    public void initialize(ValidIban constraintAnnotation) {
        ibanValidator = IBANValidator.getInstance();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false; // o true si permites null
        return value.startsWith("ES") && ibanValidator.isValid(value);
    }
}