package com.tagme.tagme_bank_back.domain.service;

import com.tagme.tagme_bank_back.domain.dto.CreditCardPaymentDto;

import java.math.BigDecimal;

public interface PaymentService {
    BigDecimal processCreditCardPayment(CreditCardPaymentDto creditCardPaymentDto);
}
