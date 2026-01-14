package com.tagme.tagme_bank_back.controller.webModel.request;

import jakarta.validation.constraints.NotBlank;

public record CreditCardRequest(
        @NotBlank(message = "Card number must not be blank")
        String number,
        @NotBlank(message = "Expiration date must not be blank")
        String expirationDate,
        @NotBlank(message = "CVV must not be blank")
        String cvv,
        @NotBlank(message = "Full name must not be blank")
        String fullName
) {
}
