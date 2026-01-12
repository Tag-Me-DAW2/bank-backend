package com.tagme.tagme_bank_back.controller.webModel.response;

public record CreditCardResponse(
        Long id,
        String number,
        String expirationDate,
        String cvv,
        String fullName
) {
}
