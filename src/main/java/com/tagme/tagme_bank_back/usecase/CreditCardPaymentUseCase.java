package com.tagme.tagme_bank_back.usecase;

import com.tagme.tagme_bank_back.domain.dto.CreditCardPaymentDto;

import java.math.BigDecimal;

public interface CreditCardPaymentUseCase {
    void execute(CreditCardPaymentDto creditCardPaymentDto);
}
