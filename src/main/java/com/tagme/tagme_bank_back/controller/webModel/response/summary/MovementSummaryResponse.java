package com.tagme.tagme_bank_back.controller.webModel.response.summary;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MovementSummaryResponse(
        Long id,
        String type,
        String origin,
        LocalDate date,
        BigDecimal amount
) {
}
