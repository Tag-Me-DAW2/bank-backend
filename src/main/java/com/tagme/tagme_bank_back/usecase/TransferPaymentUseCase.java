package com.tagme.tagme_bank_back.usecase;

import com.tagme.tagme_bank_back.domain.dto.TransferPaymentDto;

public interface TransferPaymentUseCase {
    void execute(TransferPaymentDto transferPaymentDto);
}
