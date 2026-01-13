package com.tagme.tagme_bank_back.controller.mapper;

import com.tagme.tagme_bank_back.controller.webModel.response.BankAccountResponse;
import com.tagme.tagme_bank_back.domain.model.BankAccount;

public class BankAccountMapper {
    public static BankAccountResponse fromBankAccountToBankAccountResponse(BankAccount bankAccount) {
        if (bankAccount == null) {
            return null;
        } else {
            return new BankAccountResponse(
                    bankAccount.getId(),
                    bankAccount.getIban(),
                    bankAccount.getBalance(),
                    bankAccount.getMovements().stream()
                            .map(MovementMapper::fromMovementToMovementSummaryResponse)
                            .toList(),
                    bankAccount.getCreditCards().stream()
                            .map(CreditCardMapper::fromCreditCardToCreditCardSummaryResponse)
                            .toList()
            );
        }
    }
}
