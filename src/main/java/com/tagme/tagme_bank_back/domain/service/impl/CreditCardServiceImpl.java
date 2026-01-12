package com.tagme.tagme_bank_back.domain.service.impl;

import com.tagme.tagme_bank_back.domain.exception.NotFoundException;
import com.tagme.tagme_bank_back.domain.model.CreditCard;
import com.tagme.tagme_bank_back.domain.repository.CreditCardRepository;
import com.tagme.tagme_bank_back.domain.service.CreditCardService;

public class CreditCardServiceImpl implements CreditCardService {
    private final CreditCardRepository creditCardRepository;

    public CreditCardServiceImpl(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    @Override
    public CreditCard getById(Long id) {
        if(id == null) {
            throw new RuntimeException("Credit Card id cannot be null");
        }

        return creditCardRepository.findById(id).orElseThrow(() -> new NotFoundException("Credit Card Not Found"));
    }

    @Override
    public Boolean validateCreditCard(CreditCard creditCard) {
        return creditCardRepository.validateCreditCard(creditCard);
    }
}
