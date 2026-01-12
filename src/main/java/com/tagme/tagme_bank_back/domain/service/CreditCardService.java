package com.tagme.tagme_bank_back.domain.service;

import com.tagme.tagme_bank_back.domain.model.CreditCard;

public interface CreditCardService {
    CreditCard getById(Long id);
    Boolean validateCreditCard(CreditCard creditCard);
}
