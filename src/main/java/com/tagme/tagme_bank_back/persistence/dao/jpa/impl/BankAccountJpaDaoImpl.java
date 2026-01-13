package com.tagme.tagme_bank_back.persistence.dao.jpa.impl;

import com.tagme.tagme_bank_back.persistence.dao.jpa.BankAccountJpaDao;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.BankAccountJpaEntity;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.MovementJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

public class BankAccountJpaDaoImpl implements BankAccountJpaDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<BankAccountJpaEntity> findByIban(String iban) {
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
            ORDER BY m.date DESC
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
    public BankAccountJpaEntity update(BankAccountJpaEntity entity) {
        BankAccountJpaEntity managed = entityManager.find(BankAccountJpaEntity.class, entity.getId());
        if (managed != null) {
            managed.setBalance(entity.getBalance());
        }
        return managed;
    }

    @Override
    public void deleteById(Long id) {
        BankAccountJpaEntity entity = entityManager.find(BankAccountJpaEntity.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
}
