package com.tagme.tagme_bank_back.persistence.dao.jpa.impl;

import com.tagme.tagme_bank_back.persistence.dao.jpa.BankAccountJpaDao;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.BankAccountJpaEntity;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.MovementJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class BankAccountJpaDaoImpl implements BankAccountJpaDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<BankAccountJpaEntity> findByIban(String iban) {
        System.out.println("dao: "+iban);

        String query = "SELECT b FROM BankAccountJpaEntity b WHERE b.iban = :iban";
        try {
            BankAccountJpaEntity bankAccountJpaEntity = entityManager.createQuery(query, BankAccountJpaEntity.class)
                    .setParameter("iban", iban)
                    .getSingleResult();
            return Optional.ofNullable(bankAccountJpaEntity);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Boolean existsByIbanAndClientUsername(String iban, String username) {
        String query = "SELECT COUNT(b) FROM BankAccountJpaEntity b WHERE b.iban = :iban AND b.client.username = :username";
        Long count = entityManager.createQuery(query, Long.class)
                .setParameter("iban", iban)
                .setParameter("username", username)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public List<BankAccountJpaEntity> findByClientId(Long clientId) {
        List<BankAccountJpaEntity> accounts = entityManager.createQuery("""
        SELECT b
        FROM BankAccountJpaEntity b
        WHERE b.client.id = :clientId
        """, BankAccountJpaEntity.class)
                .setParameter("clientId", clientId)
                .getResultList();

        accounts.forEach(this::loadLastMovements);

        return accounts;
    }

    private void loadLastMovements(BankAccountJpaEntity account) {
        List<MovementJpaEntity> lastMovements =
                entityManager.createQuery("""
            SELECT m
            FROM MovementJpaEntity m
            WHERE m.bankAccount.id = :accountId
            ORDER BY m.date DESC, m.id DESC
            """, MovementJpaEntity.class)
                        .setParameter("accountId", account.getId())
                        .setMaxResults(4)
                        .getResultList();

        account.setMovements(lastMovements);
    }

    @Override
    public Long count() {
        return entityManager.createQuery("SELECT COUNT(b) FROM BankAccountJpaEntity b", Long.class).getSingleResult();
    }

    @Override
    public Optional<BankAccountJpaEntity> findById(Long id) {
        return Optional.ofNullable(entityManager.find(BankAccountJpaEntity.class, id));
    }

    @Override
    public BankAccountJpaEntity insert(BankAccountJpaEntity bankAccountJpaEntity) {
        entityManager.persist(bankAccountJpaEntity);
        return bankAccountJpaEntity;
    }

    @Override
    public BankAccountJpaEntity update(BankAccountJpaEntity entity) {
        BankAccountJpaEntity managedEntity = entityManager.find(BankAccountJpaEntity.class, entity.getId());
        if (managedEntity == null) {
            throw new IllegalArgumentException("La cuenta bancaria con ID " + entity.getId() + " no existe.");
        }
        return entityManager.merge(entity);
    }

    @Override
    public void deleteById(Long id) {
        BankAccountJpaEntity entity = entityManager.find(BankAccountJpaEntity.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

    @Override
    public Optional<BigDecimal> findBalanceByIban(String iban) {
        try {
            BigDecimal balance = entityManager.createQuery("""
                SELECT b.balance
                FROM BankAccountJpaEntity b
                WHERE b.iban = :iban
                """, BigDecimal.class)
                    .setParameter("iban", iban)
                    .getSingleResult();
            return Optional.ofNullable(balance);
        }
        catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> findIbanByCreditCardNumber(String creditCardNumber) {
        try {
            String iban = entityManager.createQuery("""
                SELECT b.iban
                FROM BankAccountJpaEntity b
                JOIN b.creditCards c
                WHERE c.number = :creditCardNumber
                """, String.class)
                    .setParameter("creditCardNumber", creditCardNumber)
                    .getSingleResult();
            return Optional.ofNullable(iban);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
