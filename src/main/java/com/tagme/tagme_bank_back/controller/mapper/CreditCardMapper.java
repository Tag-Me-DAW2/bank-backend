package com.tagme.tagme_bank_back.controller.mapper;

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
}
