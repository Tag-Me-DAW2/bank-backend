package com.tagme.tagme_bank_back.controller.webModel.response;

import com.tagme.tagme_bank_back.controller.webModel.response.summary.CreditCardSummaryResponse;
import com.tagme.tagme_bank_back.controller.webModel.response.summary.MovementSummaryResponse;

import java.math.BigDecimal;
import java.util.List;

public record BankAccountResponse(
        Long id,
        String iban,
        BigDecimal balance,
        List<MovementSummaryResponse> movements,
        List<CreditCardSummaryResponse> cards
) {
}
