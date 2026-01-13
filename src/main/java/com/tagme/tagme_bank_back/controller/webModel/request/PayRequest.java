package com.tagme.tagme_bank_back.controller.webModel.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record PayRequest (
    @Min(value = 0, message = "The amount must be positive")
    BigDecimal amount,
    @Size(min = 3, message = "The concept must be at least 3 characters long")
    @NotBlank(message = "The concept must not be blank")
    String concept
)
{}
