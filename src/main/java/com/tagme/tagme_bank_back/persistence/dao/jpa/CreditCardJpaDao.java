package com.tagme.tagme_bank_back.persistence.dao.jpa;

import com.tagme.tagme_bank_back.domain.model.CreditCard;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.BankAccountJpaEntity;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.CreditCardJpaEntity;

public interface CreditCardJpaDao extends GenericJpaDao<CreditCardJpaEntity> {
    Boolean validateCreditCard(CreditCardJpaEntity creditCard);
    CreditCardJpaEntity insert(CreditCardJpaEntity creditCard, Long bankAccountId);
}
