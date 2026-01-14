package com.tagme.tagme_bank_back.controller.webModel.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CreditCardPaymentRequest(
        @NotNull
        @Valid
        AuthRequest authRequest,
        @NotNull
        @Valid
        CreditCardRequest creditCardRequest,
        @NotNull
        @Valid
        TransferRequest transferRequest,
        @NotNull
        @Valid
        PayRequest payRequest
) {
}
