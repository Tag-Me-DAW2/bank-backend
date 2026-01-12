package com.tagme.tagme_bank_back.domain.repository;

import com.tagme.tagme_bank_back.domain.model.CreditCard;

import java.util.Optional;

public interface CreditCardRepository {
    Optional<CreditCard> findById(Long id);
    Boolean validateCreditCard(CreditCard creditCard);
}
