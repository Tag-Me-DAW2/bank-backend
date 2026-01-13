package com.tagme.tagme_bank_back.controller.mapper;

import com.tagme.tagme_bank_back.controller.webModel.response.MovementResponse;
import com.tagme.tagme_bank_back.controller.webModel.response.summary.MovementSummaryResponse;
import com.tagme.tagme_bank_back.domain.model.Movement;

public class MovementMapper {
    public static MovementResponse fromMovementToMovementResponse(Movement movement) {
        if (movement == null) {
            return null;
        } else {
            return new MovementResponse(
                    movement.getId(),
                    movement.getType().toString(),
                    movement.getOrigin().toString(),
                    CreditCardMapper.fromCreditCardToCreditCardSummaryResponse(movement.getoCreditCard()),
                    movement.getDate(),
                    movement.getAmount(),
                    movement.getConcept()
            );
        }
    }

    public static MovementSummaryResponse fromMovementToMovementSummaryResponse(Movement movement) {
        if (movement == null) {
            return null;
        } else {
            return new MovementSummaryResponse(
                    movement.getId(),
                    movement.getType().toString(),
                    movement.getOrigin().toString(),
                    movement.getDate(),
                    movement.getAmount()
            );
        }
    }
}
