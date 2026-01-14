package com.tagme.tagme_bank_back.controller.webModel.request;

import com.tagme.tagme_bank_back.domain.validation.ValidIban;
import jakarta.validation.constraints.NotBlank;

public record TransferRequest(
    @ValidIban
    String iban
) {
}
