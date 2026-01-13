package com.tagme.tagme_bank_back.controller.webModel.response.summary;

public record CreditCardSummaryResponse(
        Long id,
        String number,
        String fullName
) {
}
