package com.tagme.tagme_bank_back.persistence.dao.jpa;

import com.tagme.tagme_bank_back.domain.model.Page;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.BankAccountJpaEntity;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.MovementJpaEntity;

import java.util.List;

public interface MovementJpaDao extends GenericJpaDao<MovementJpaEntity> {
    List<MovementJpaEntity> findAll(int page, int size);
    List<MovementJpaEntity> findAllByAccountId(Long bankAccountId, int page, int size);
    MovementJpaEntity insert(MovementJpaEntity movementJpaEntity, Long bankAccountId);
}
