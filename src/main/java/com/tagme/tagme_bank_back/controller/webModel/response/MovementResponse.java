package com.tagme.tagme_bank_back.controller.webModel.response;

import com.tagme.tagme_bank_back.controller.webModel.response.summary.CreditCardSummaryResponse;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MovementResponse(
        Long id,
        String type,
        String origin,
        CreditCardSummaryResponse creditCard,
        LocalDate date,
        BigDecimal amount,
        String concept
) {
}
