package com.tagme.tagme_bank_back.controller.webModel.response;

import java.math.BigDecimal;

public record PaymentResponse(
        String status,
        String message,
        BigDecimal balance
) {
}
