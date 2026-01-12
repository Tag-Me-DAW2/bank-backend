package com.tagme.tagme_bank_back.persistence.dao.jpa.impl;

import com.tagme.tagme_bank_back.persistence.dao.jpa.CreditCardJpaDao;
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
                "AND c.cvv = :cvv";

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
}
