package com.tagme.tagme_bank_back.persistence.repository;

import com.tagme.tagme_bank_back.domain.model.CreditCard;
import com.tagme.tagme_bank_back.domain.repository.CreditCardRepository;
import com.tagme.tagme_bank_back.persistence.dao.jpa.CreditCardJpaDao;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.CreditCardJpaEntity;
import com.tagme.tagme_bank_back.persistence.mapper.CreditCardMapper;


import java.util.Optional;

public class CreditCardRepositoryImpl implements CreditCardRepository {
    private final CreditCardJpaDao creditCardDao;

    public CreditCardRepositoryImpl(CreditCardJpaDao creditCardDao) {
        this.creditCardDao = creditCardDao;
    }

    @Override
    public Optional<CreditCard> findById(Long id) {
        return creditCardDao.findById(id).map(CreditCardMapper::fromCreditCardJpaEntityToCreditCard);
    }

    @Override
    public Boolean validateCreditCard(CreditCard creditCard) {
        CreditCardJpaEntity creditCardEntity = CreditCardMapper.fromCreditCardToCreditCardJpaEntity(creditCard);
        return creditCardDao.validateCreditCard(creditCardEntity);
    }

    @Override
    public Optional<Long> getIdByCreditCardNumber(String cardNumber) {
        return creditCardDao.getIdByCreditCardNumber(cardNumber);
    }
}
