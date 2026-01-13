package com.tagme.tagme_bank_back.domain.service;

import com.tagme.tagme_bank_back.domain.model.Movement;
import com.tagme.tagme_bank_back.domain.model.Page;

public interface MovementService {
    Page<Movement> getAll(int page, int size);
    Movement getById(Long id);
    Movement create(Movement movement, Long accountId);
}
