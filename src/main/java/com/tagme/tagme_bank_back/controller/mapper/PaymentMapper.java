package com.tagme.tagme_bank_back.controller.mapper;

import com.tagme.tagme_bank_back.controller.webModel.request.CreditCardPaymentRequest;
import com.tagme.tagme_bank_back.controller.webModel.request.TransferPaymentRequest;
import com.tagme.tagme_bank_back.domain.dto.CreditCardPaymentDto;
import com.tagme.tagme_bank_back.domain.dto.TransferPaymentDto;

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

    public static TransferPaymentDto fromTransferPaymentRequest(TransferPaymentRequest request) {
        if (request == null) {
            return null;
        }

        return new TransferPaymentDto(
                AuthMapper.toDto(request.authRequest()),
                TransferMapper.toDto(request.originTransferRequest()),
                TransferMapper.toDto(request.destinationTransferRequest()),
                PayMapper.toDto(request.payRequest())
        );
    }
}
