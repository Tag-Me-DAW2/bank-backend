package com.tagme.tagme_bank_back.persistence.mapper;

import com.tagme.tagme_bank_back.domain.model.CreditCard;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.CreditCardJpaEntity;

public class CreditCardMapper {
    public static CreditCard fromCreditCardJpaEntityToCreditCard(CreditCardJpaEntity creditCard) {
        if (creditCard == null) {
            return null;
        }
        return new CreditCard(
                creditCard.getId(),
                creditCard.getNumber(),
                creditCard.getExpirationDate(),
                creditCard.getCvv(),
                creditCard.getFullName()
        );
    }

    public static CreditCardJpaEntity fromCreditCardToCreditCardJpaEntity(CreditCard creditCard) {
        if (creditCard == null) {
            return null;
        }

        return new CreditCardJpaEntity(
                creditCard.getId(),
                creditCard.getNumber(),
                creditCard.getExpirationDate(),
                creditCard.getCvv(),
                creditCard.getFullName()
        );
    }
}
