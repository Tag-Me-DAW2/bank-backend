package com.tagme.tagme_bank_back.usecase.impl;

import com.tagme.tagme_bank_back.domain.dto.TransferPaymentDto;
import com.tagme.tagme_bank_back.domain.exception.InsufficientBalanceException;
import com.tagme.tagme_bank_back.domain.model.BankAccount;
import com.tagme.tagme_bank_back.domain.model.MovementOrigin;
import com.tagme.tagme_bank_back.domain.service.AuthService;
import com.tagme.tagme_bank_back.domain.service.BankAccountService;
import com.tagme.tagme_bank_back.usecase.TransferPaymentUseCase;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public class TransferPaymentUseCaseImpl implements TransferPaymentUseCase {
    private final BankAccountService bankAccountService;
    private final AuthService authService;

    public TransferPaymentUseCaseImpl(BankAccountService bankAccountService, AuthService authService) {
        this.bankAccountService = bankAccountService;
        this.authService = authService;
    }

    @Override
    @Transactional
    public void execute(TransferPaymentDto transferPaymentDto) {
        String username = transferPaymentDto.authDto().username();
        String apiKey = transferPaymentDto.authDto().apiKey();

        String ibanOrigin = transferPaymentDto.originTransferDto().iban();
        String ibanDestination = transferPaymentDto.destinationTransferDto().iban();

        BigDecimal amount = transferPaymentDto.payDto().amount();
        String concept = transferPaymentDto.payDto().concept();

        // Comprobar apiKey
        authService.isApiKeyValid(apiKey);

        // Comprobar Iban
        if (ibanOrigin.equals(ibanDestination)) {
            throw new IllegalArgumentException("Las cuentas de origen y destino no pueden ser las mismas");
        }

        bankAccountService.checkOwner(ibanOrigin, username);

        // Comprobar saldo
        BigDecimal originBalance = bankAccountService.getBalanceByIban(ibanOrigin);
        checkAvaliableBalance(originBalance, amount);

        // Realizar transferencia
        BankAccount originAccount = bankAccountService.getByIban(ibanOrigin);
        BankAccount destinationAccount = bankAccountService.getByIban(ibanDestination);

        bankAccountService.withdrawByTransfer(originAccount, amount, concept);
        bankAccountService.deposit(destinationAccount, amount, MovementOrigin.TRANSFER, concept);
    }

    private void checkAvaliableBalance(BigDecimal clientBalance, BigDecimal paymentAmount) {
        if (clientBalance.subtract(paymentAmount).compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientBalanceException("Saldo insuficiente");
        }

    }
}
