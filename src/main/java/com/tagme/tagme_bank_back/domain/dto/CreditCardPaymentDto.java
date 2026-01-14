package com.tagme.tagme_bank_back.domain.dto;

import com.tagme.tagme_bank_back.domain.model.CreditCard;

public record CreditCardPaymentDto(
        AuthDto auth,
        CreditCard creditCard,
        TransferDto transferDto,
        PayDto payDto
) {
}
