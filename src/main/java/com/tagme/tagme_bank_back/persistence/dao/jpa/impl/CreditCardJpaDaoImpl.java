package com.tagme.tagme_bank_back.persistence.dao.jpa.impl;

import com.tagme.tagme_bank_back.domain.exception.NotFoundException;
import com.tagme.tagme_bank_back.persistence.dao.jpa.CreditCardJpaDao;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.BankAccountJpaEntity;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.CreditCardJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Optional;

public class CreditCardJpaDaoImpl implements CreditCardJpaDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Boolean validateCreditCard(CreditCardJpaEntity creditCard) {
        String sql = "SELECT COUNT(c) FROM CreditCardJpaEntity c " +
                "WHERE c.number = :number " +
                "AND c.fullName = :fullName " +
                "AND c.expirationDate = :expirationDate " +
                "AND c.cvv = :cvv ";

        Long count = entityManager.createQuery(sql, Long.class)
                .setParameter("number", creditCard.getNumber())
                .setParameter("fullName", creditCard.getFullName())
                .setParameter("expirationDate", creditCard.getExpirationDate())
                .setParameter("cvv", creditCard.getCvv())
                .getSingleResult();

        return count != null && count > 0;
    }

    @Override
    public Long count() {
        return entityManager.createQuery("SELECT COUNT(c) FROM CreditCardJpaEntity c", Long.class)
                .getSingleResult();
    }

    @Override
    public Optional<CreditCardJpaEntity> findById(Long id) {
        return Optional.ofNullable(entityManager.find(CreditCardJpaEntity.class, id));
    }

    @Override
    public CreditCardJpaEntity insert(CreditCardJpaEntity entity, Long bankAccountId) {
        BankAccountJpaEntity bankAccount = entityManager.find(BankAccountJpaEntity.class, bankAccountId);
        if (bankAccount == null) {
            throw new NotFoundException("La cuenta bancaria con ID " + bankAccountId + " no existe.");
        }
        entity.setBankAccount(bankAccount);
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public CreditCardJpaEntity update(CreditCardJpaEntity entity) {
        if (entityManager.find(CreditCardJpaEntity.class, entity.getId()) == null) {
            throw new NotFoundException("La tarjeta de crédito con ID " + entity.getId() + " no existe.");
        } else {
            return entityManager.merge(entity);
        }
    }

    @Override
    public void deleteById(Long id) {
        CreditCardJpaEntity entity = entityManager.find(CreditCardJpaEntity.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

    @Override
    public Optional<Long> getIdByCreditCardNumber(String cardNumber) {
        String sql = "SELECT c.id FROM CreditCardJpaEntity c " +
                "WHERE c.number = :number ";

        Long result = entityManager.createQuery(sql, Long.class)
                .setParameter("number", cardNumber)
                .getSingleResult();

        return Optional.ofNullable(result);
    }
}
