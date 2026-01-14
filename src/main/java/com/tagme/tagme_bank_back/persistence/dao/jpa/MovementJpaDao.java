package com.tagme.tagme_bank_back.persistence.dao.jpa;

import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.MovementJpaEntity;

import java.time.LocalDate;
import java.util.List;

public interface MovementJpaDao extends GenericJpaDao<MovementJpaEntity> {
    List<MovementJpaEntity> findAll(int page, int size);
    List<MovementJpaEntity> findAllByAccountId(Long bankAccountId, int page, int size);
    List<MovementJpaEntity> findMonthlyMovements(Long bankAccountId, LocalDate startOfTheMonth, LocalDate startOfTheNextMonth, int page, int size);
    MovementJpaEntity insert(MovementJpaEntity movementJpaEntity, Long bankAccountId);
}
