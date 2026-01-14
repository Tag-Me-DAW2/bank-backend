package com.tagme.tagme_bank_back.controller.mapper;

import com.tagme.tagme_bank_back.controller.webModel.request.CreditCardPaymentRequest;
import com.tagme.tagme_bank_back.domain.dto.CreditCardPaymentDto;

public class PaymentMapper {
    public static CreditCardPaymentDto fromCreditCardPaymentRequest(CreditCardPaymentRequest request) {
        if (request == null) {
            return null;
        }

        return new CreditCardPaymentDto(
                AuthMapper.toDto(request.authRequest()),
                CreditCardMapper.fromCreditCardRequestToCreditCard(request.creditCardRequest()),
                TransferMapper.toDto(request.transferRequest()),
                PayMapper.toDto(request.payRequest())
        );
    }
}
