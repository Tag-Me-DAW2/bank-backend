package com.tagme.tagme_bank_back.persistence.dao.jpa.impl;

import com.tagme.tagme_bank_back.domain.exception.NotFoundException;
import com.tagme.tagme_bank_back.persistence.dao.jpa.MovementJpaDao;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.BankAccountJpaEntity;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.MovementJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.testcontainers.shaded.org.checkerframework.checker.units.qual.s;

public class MovementJpaDaoImpl implements MovementJpaDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<MovementJpaEntity> findAll(int page, int size) {
        int pageIndex = Math.max(page - 1, 0);

        String sql = "SELECT m FROM MovementJpaEntity m ORDER BY m.id";
        TypedQuery<MovementJpaEntity> query = entityManager
                .createQuery(sql, MovementJpaEntity.class)
                .setFirstResult(pageIndex * size)
                .setMaxResults(size);

        return query.getResultList();
    }

    @Override
    public List<MovementJpaEntity> findAllByAccountId(Long bankAccountId, int page, int size) {
        int pageIndex = Math.max(page - 1, 0);

        String sql = "SELECT m FROM MovementJpaEntity m WHERE m.bankAccount.id = :bankAccountId ORDER BY m.id";
        TypedQuery<MovementJpaEntity> query = entityManager
                .createQuery(sql, MovementJpaEntity.class)
                .setParameter("bankAccountId", bankAccountId)
                .setFirstResult(pageIndex * size)
                .setMaxResults(size);

        return query.getResultList();
    }

    @Override
    public Long count() {
        String sql = "SELECT COUNT(m) FROM MovementJpaEntity m";
        TypedQuery<Long> query = entityManager.createQuery(sql, Long.class);
        return query.getSingleResult();
    }

    @Override
    public Optional<MovementJpaEntity> findById(Long id) {
        return Optional.ofNullable(entityManager.find(MovementJpaEntity.class, id));
    }

    @Override
    public MovementJpaEntity insert(MovementJpaEntity entity, Long bankAccountId) {
        BankAccountJpaEntity bankAccountJpaEntity = entityManager.find(BankAccountJpaEntity.class, bankAccountId);
        if (bankAccountJpaEntity == null) {
            throw new NotFoundException("Bank account with ID " + bankAccountId + " does not exist.");
        }
        entity.setBankAccount(bankAccountJpaEntity);
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public MovementJpaEntity update(MovementJpaEntity entity) {
        MovementJpaEntity managedEntity = entityManager.find(MovementJpaEntity.class, entity.getId());
        if (managedEntity == null) {
            throw new IllegalArgumentException("Movement with ID " + entity.getId() + " does not exist.");
        }
        return entityManager.merge(entity);
    }

    @Override
    public void deleteById(Long id) {
        MovementJpaEntity entity = entityManager.find(MovementJpaEntity.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

    @Override
    public List<MovementJpaEntity> findMonthlyMovements(Long bankAccountId, LocalDate startOfTheMonth,
            LocalDate startOfTheNextMonth, int page, int size) {
        int pageIndex = Math.max(page - 1, 0);

        String sql = "SELECT m FROM MovementJpaEntity m WHERE m.bankAccount.id = :bankAccountId AND m.date >= :startOfTheMonth AND m.date < :startOfTheNextMonth ORDER BY m.date DESC, m.id DESC";
        TypedQuery<MovementJpaEntity> query = entityManager
                .createQuery(sql, MovementJpaEntity.class)
                .setParameter("bankAccountId", bankAccountId)
                .setParameter("startOfTheMonth", startOfTheMonth)
                .setParameter("startOfTheNextMonth", startOfTheNextMonth)
                .setFirstResult(pageIndex * size)
                .setMaxResults(size);

        return query.getResultList();
    }
}