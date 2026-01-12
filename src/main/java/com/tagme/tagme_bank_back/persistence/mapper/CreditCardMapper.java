package com.tagme.tagme_bank_back.persistence.mapper;

import com.tagme.tagme_bank_back.domain.model.CreditCard;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.CreditCardJpaEntity;

public class CreditCardMapper {
    public static CreditCard fromCreditCardJpaEntityToCreditCard(CreditCardJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        return new CreditCard(
                entity.getId(),
                entity.getNumber(),
                entity.getExpirationDate(),
                entity.getCvv(),
                entity.getFullName()
        );
    }

    public static CreditCardJpaEntity fromCreditCardToCreditCardJpaEntity(CreditCard creditCard) {
        if (creditCard == null) {
            return null;
        }
        CreditCardJpaEntity entity = new CreditCardJpaEntity();
        entity.setId(creditCard.getId());
        entity.setNumber(creditCard.getNumber());
        entity.setExpirationDate(creditCard.getExpirationDate());
        entity.setCvv(creditCard.getCvv());
        entity.setFullName(creditCard.getFullName());
        return entity;
    }
}
