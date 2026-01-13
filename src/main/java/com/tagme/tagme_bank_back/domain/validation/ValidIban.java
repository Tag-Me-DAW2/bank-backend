package com.tagme.tagme_bank_back.domain.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IbanValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidIban {
    String message() default "IBAN inválido o no empieza por ES";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}