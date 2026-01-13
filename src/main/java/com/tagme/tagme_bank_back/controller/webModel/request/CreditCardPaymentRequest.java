package com.tagme.tagme_bank_back.controller.webModel.request;

public record CreditCardPaymentRequest(
        AuthRequest authRequest,
        CreditCardRequest creditCardRequest,
        TransferRequest transferRequest,
        PayRequest payRequest
) {
}
