package com.tagme.tagme_bank_back.persistence.mapper;

import com.tagme.tagme_bank_back.domain.model.*;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.BankAccountJpaEntity;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.ClientJpaEntity;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.CreditCardJpaEntity;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.MovementJpaEntity;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class BankAccountMapper {
    public static BankAccountJpaEntity fromBankAccountToBankAccountJpaEntity(BankAccount bankAccount) {
        if (bankAccount == null) {
            return null;
        }

        ClientJpaEntity client = ClientMapper.fromClientToClientJpaEntity(bankAccount.getClient());
        List<MovementJpaEntity> movements = bankAccount.getMovements().stream().map(MovementMapper::fromMovementToMovementJpaEntity).toList();
        List<CreditCardJpaEntity> cards = bankAccount.getCreditCards().stream().map(CreditCardMapper::fromCreditCardToCreditCardJpaEntity).toList();

        BankAccountJpaEntity bankAccountJpaEntity = new BankAccountJpaEntity(
                bankAccount.getId(),
                bankAccount.getIban(),
                bankAccount.getBalance(),
                client,
                List.of(),
                List.of()
        );

        bankAccountJpaEntity.setCreditCards(new BankAccountMapper().cardSetAccount(cards, bankAccountJpaEntity));
        bankAccountJpaEntity.setMovements(new BankAccountMapper().movementsSetAccount(movements, bankAccountJpaEntity));

        return bankAccountJpaEntity;
    }

    public static BankAccount fromBankAccountJpaEntityToBankAccount(BankAccountJpaEntity bankAccountJpaEntity) {
        if (bankAccountJpaEntity.getId() == null) {
            return null;
        }

        List<Movement> movements = bankAccountJpaEntity.getMovements().stream().map(MovementMapper::fromMovementJpaEntityToMovement).toList();
        List<CreditCard> cards = bankAccountJpaEntity.getCreditCards().stream().map(CreditCardMapper::fromCreditCardJpaEntityToCreditCard).toList();
        Client client = ClientMapper.fromClientJpaEntityToClient(bankAccountJpaEntity.getClient());

        return new BankAccount(
                bankAccountJpaEntity.getId(),
                bankAccountJpaEntity.getIban(),
                bankAccountJpaEntity.getBalance(),
                client,
                cards,
                movements
        );
    }

    private List<CreditCardJpaEntity> cardSetAccount(List<CreditCardJpaEntity> creditCards, BankAccountJpaEntity bankAccountJpaEntity) {
        if (creditCards == null) {
            return List.of();
        }

        return creditCards.stream()
                .peek(card -> card.setBankAccount(bankAccountJpaEntity))
                .collect(toList());
    }

    private List<MovementJpaEntity> movementsSetAccount(List<MovementJpaEntity> movements, BankAccountJpaEntity bankAccountJpaEntity) {
        if (movements == null) {
            return List.of();
        }

        return movements.stream()
                .peek(movement -> movement.setBankAccount(bankAccountJpaEntity))
                .collect(toList());
    }
}
