package com.tagme.tagme_bank_back.controller.webModel.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record TransferPaymentRequest(
        @NotNull
        @Valid
        AuthRequest authRequest,
        @NotNull
        @Valid
        TransferRequest originTransferRequest,
        @NotNull
        @Valid
        TransferRequest destinationTransferRequest,
        @NotNull
        @Valid
        PayRequest payRequest
) {
}
