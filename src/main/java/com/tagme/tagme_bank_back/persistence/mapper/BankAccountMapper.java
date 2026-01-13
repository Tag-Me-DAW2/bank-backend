package com.tagme.tagme_bank_back.persistence.mapper;

import com.tagme.tagme_bank_back.domain.model.*;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.BankAccountJpaEntity;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.ClientJpaEntity;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.CreditCardJpaEntity;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.MovementJpaEntity;

import java.util.List;

public class BankAccountMapper {
    public static BankAccountJpaEntity fromBankAccountToBankAccountJpaEntity(BankAccount bankAccount) {
        if (bankAccount == null) {
            return null;
        }

        List<MovementJpaEntity> movements = bankAccount.getMovements().stream().map(MovementMapper::fromMovementToMovementJpaEntity).toList();
        List<CreditCardJpaEntity> cards = bankAccount.getCreditCards().stream().map(CreditCardMapper::fromCreditCardToCreditCardJpaEntity).toList();
        ClientJpaEntity client = ClientMapper.fromClientToClientJpaEntity(bankAccount.getClient());

        return new BankAccountJpaEntity(
                bankAccount.getId(),
                bankAccount.getIban(),
                bankAccount.getBalance(),
                client,
                cards,
                movements
        );
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
}
