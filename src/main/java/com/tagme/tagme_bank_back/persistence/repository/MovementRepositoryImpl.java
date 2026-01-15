package com.tagme.tagme_bank_back.persistence.repository;

import com.tagme.tagme_bank_back.domain.model.Movement;
import com.tagme.tagme_bank_back.domain.model.Page;
import com.tagme.tagme_bank_back.domain.repository.MovementRepository;
import com.tagme.tagme_bank_back.persistence.dao.jpa.MovementJpaDao;
import com.tagme.tagme_bank_back.persistence.mapper.MovementMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MovementRepositoryImpl implements MovementRepository {
    private final MovementJpaDao movementJpaDao;

    public MovementRepositoryImpl(MovementJpaDao movementJpaDao) {
        this.movementJpaDao = movementJpaDao;
    }

    @Override
    public Page<Movement> findAll(int page, int size) {
        List<Movement> content = movementJpaDao.findAll(page, size).stream()
                .map(MovementMapper::fromMovementJpaEntityToMovement)
                .toList();

        long totalElements = movementJpaDao.count();
        return new Page<>(content, page, size, totalElements);
    }

    @Override
    public Page<Movement> findAllByAccountId(Long accountId, int page, int size) {
        List<Movement> content = movementJpaDao.findAllByAccountId(accountId, page, size).stream()
                .map(MovementMapper::fromMovementJpaEntityToMovement)
                .toList();

        long totalElements = movementJpaDao.count();
        return new Page<>(content, page, size, totalElements);
    }

    @Override
    public Optional<Movement> findById(Long id) {
        return movementJpaDao.findById(id).map(MovementMapper::fromMovementJpaEntityToMovement);
    }

    @Override
    public Movement save(Movement movement, Long accountId) {
        if (movement.getId() == null) {
            return MovementMapper.fromMovementJpaEntityToMovement(
                    movementJpaDao.insert(MovementMapper.fromMovementToMovementJpaEntity(movement), accountId));
        } else {
            return MovementMapper.fromMovementJpaEntityToMovement(
                    movementJpaDao.update(MovementMapper.fromMovementToMovementJpaEntity(movement)));
        }
    }

    @Override
    public Page<Movement> findMonthlyMovements(Long accountId, LocalDate startOfTheMonth, LocalDate startOfTheNextMonth,
            int page, int size) {
        List<Movement> content = movementJpaDao
                .findMonthlyMovements(accountId, startOfTheMonth, startOfTheNextMonth, page, size).stream()
                .map(MovementMapper::fromMovementJpaEntityToMovement)
                .toList();
        long totalElements = movementJpaDao.count();
        return new Page<>(content, page, size, totalElements);
    }

    @Override
    public Page<Movement> findByCardId(Long cardId, int page, int size) {
        List<Movement> content = movementJpaDao.findByCardId(cardId, page, size).stream()
                .map(MovementMapper::fromMovementJpaEntityToMovement)
                .toList();

        long totalElements = movementJpaDao.count();
        return new Page<>(content, page, size, totalElements);
    }
}
