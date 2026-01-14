package com.tagme.tagme_bank_back.domain.dto;

import java.math.BigDecimal;

public record PayDto(
        BigDecimal amount,
        String concept
) {
}
