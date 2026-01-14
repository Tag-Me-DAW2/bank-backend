package com.tagme.tagme_bank_back.controller.mapper;

import com.tagme.tagme_bank_back.controller.webModel.request.CreditCardRequest;
import com.tagme.tagme_bank_back.controller.webModel.response.CreditCardResponse;
import com.tagme.tagme_bank_back.controller.webModel.response.summary.CreditCardSummaryResponse;
import com.tagme.tagme_bank_back.domain.model.CreditCard;

public class CreditCardMapper {
    public static CreditCardResponse fromCreditCardToCreditCardResponse(CreditCard creditCard) {
        if (creditCard == null) {
            return null;
        } else {
            return new CreditCardResponse(
                    creditCard.getId(),
                    creditCard.getNumber(),
                    creditCard.getExpirationDate(),
                    creditCard.getCvv(),
                    creditCard.getFullName()
            );
        }
    }

    public static CreditCardSummaryResponse fromCreditCardToCreditCardSummaryResponse(CreditCard creditCard) {
        if (creditCard == null) {
            return null;
        } else {
            return new CreditCardSummaryResponse(
                    creditCard.getId(),
                    creditCard.getNumber(),
                    creditCard.getFullName()
            );
        }
    }

    public static CreditCard fromCreditCardResponseToCreditCard(CreditCardResponse creditCardResponse) {
        if (creditCardResponse == null) {
            return null;
        } else {
            return new CreditCard(
                    creditCardResponse.id(),
                    creditCardResponse.number(),
                    creditCardResponse.expirationDate(),
                    creditCardResponse.cvv(),
                    creditCardResponse.fullName()
            );
        }
    }

    public static CreditCard fromCreditCardSummaryResponseToCreditCard(CreditCardSummaryResponse creditCardSummaryResponse) {
        if (creditCardSummaryResponse == null) {
            return null;
        } else {
            return new CreditCard(
                    creditCardSummaryResponse.id(),
                    creditCardSummaryResponse.number(),
                    null,
                    null,
                    creditCardSummaryResponse.fullName()
            );
        }
    }

    public static CreditCard fromCreditCardRequestToCreditCard(CreditCardRequest creditCardRequest) {
        if (creditCardRequest == null) {
            return null;
        } else {
            return new CreditCard(
                    null,
                    creditCardRequest.number(),
                    creditCardRequest.expirationDate(),
                    creditCardRequest.cvv(),
                    creditCardRequest.fullName()
            );
        }
    }
}
