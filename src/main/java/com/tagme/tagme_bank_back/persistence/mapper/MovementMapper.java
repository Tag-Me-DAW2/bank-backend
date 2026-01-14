package com.tagme.tagme_bank_back.persistence.mapper;

import com.tagme.tagme_bank_back.domain.model.Movement;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.MovementJpaEntity;

public class MovementMapper {
    public static MovementJpaEntity fromMovementToMovementJpaEntity(Movement movement) {
        if (movement == null) {
            return null;
        }

        return new MovementJpaEntity(
                movement.getId(),
                movement.getType(),
                movement.getOrigin(),
                CreditCardMapper.fromCreditCardToCreditCardJpaEntity(movement.getoCreditCard()),
                movement.getDate(),
                movement.getAmount(),
                movement.getConcept()
        );
    }

    public static Movement fromMovementJpaEntityToMovement(MovementJpaEntity movementJpaEntity) {
        if (movementJpaEntity == null) {
            return null;
        }

        return new Movement(
                movementJpaEntity.getId(),
                movementJpaEntity.getType(),
                movementJpaEntity.getOrigin(),
                CreditCardMapper.fromCreditCardJpaEntityToCreditCard(movementJpaEntity.getOriginCreditCard()),
                movementJpaEntity.getDate(),
                movementJpaEntity.getAmount(),
                movementJpaEntity.getConcept()
        );
    }
}
