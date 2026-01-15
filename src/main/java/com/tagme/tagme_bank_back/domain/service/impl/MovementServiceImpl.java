package com.tagme.tagme_bank_back.domain.service.impl;

import java.time.LocalDate;
import java.util.Date;

import com.tagme.tagme_bank_back.domain.exception.NotFoundException;
import com.tagme.tagme_bank_back.domain.model.Movement;
import com.tagme.tagme_bank_back.domain.model.Page;
import com.tagme.tagme_bank_back.domain.repository.MovementRepository;
import com.tagme.tagme_bank_back.domain.service.MovementService;

public class MovementServiceImpl implements MovementService {
    private final MovementRepository movementRepository;

    public MovementServiceImpl(MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }

    @Override
    public Page<Movement> getAll(int page, int size) {
        if (page < 1 || size < 1) {
            throw new RuntimeException("Page and size must be greater than 0");
        }

        return movementRepository.findAll(page, size);
    }

    @Override
    public Page<Movement> getAllByAccountId(Long accountId, int page, int size) {
        if (page < 1 || size < 1) {
            throw new RuntimeException("Page and size must be greater than 0");
        }

        if (accountId == null) {
            throw new RuntimeException("AccountId cannot be null");
        }

        return movementRepository.findAllByAccountId(accountId, page, size);
    }

    @Override
    public Movement getById(Long id) {
        if (id == null) {
            throw new RuntimeException("Id cannot be null");
        }

        return movementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Movement not found with id: " + id));
    }

    @Override
    public Movement create(Movement movement, Long accountId) {
        return movementRepository.save(movement, accountId);
    }

    @Override
    public Page<Movement> getMonthlyMovements(Long accountId, LocalDate date, int page, int size) {
        if (page < 1 || size < 1) {
            throw new RuntimeException("Page and size must be greater than 0");
        }

        if (accountId == null) {
            throw new RuntimeException("AccountId cannot be null");
        }

        if (date == null) {
            throw new RuntimeException("Date cannot be null");
        }

        LocalDate startOfTheMonth = date.withDayOfMonth(1);
        LocalDate startOfTheNextMonth = startOfTheMonth.plusMonths(1);
        return movementRepository.findMonthlyMovements(accountId, startOfTheMonth, startOfTheNextMonth, page, size);
    }

    @Override
    public Page<Movement> getByCardId(Long cardId, int page, int size) {
        if (page < 1 || size < 1) {
            throw new RuntimeException("Page and size must be greater than 0");
        }

        if (cardId == null) {
            throw new RuntimeException("CardId cannot be null");
        }

        return movementRepository.findByCardId(cardId, page, size);
    }
}
