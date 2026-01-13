package com.tagme.tagme_bank_back.domain.repository;

import com.tagme.tagme_bank_back.domain.model.Movement;
import com.tagme.tagme_bank_back.domain.model.Page;

import java.util.Optional;

public interface MovementRepository {
    Page<Movement> findAll(int page, int size);
    Page<Movement> findAllByAccountId(Long accountId, int page, int size);
    Optional<Movement> findById(Long id);
    Movement save(Movement movement, Long accountId);
}
