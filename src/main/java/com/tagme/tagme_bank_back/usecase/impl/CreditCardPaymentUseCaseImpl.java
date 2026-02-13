package com.tagme.tagme_bank_back.usecase.impl;

import com.tagme.tagme_bank_back.domain.dto.CreditCardPaymentDto;
import com.tagme.tagme_bank_back.domain.exception.InsufficientBalanceException;
import com.tagme.tagme_bank_back.domain.model.*;
import com.tagme.tagme_bank_back.domain.service.AuthService;
import com.tagme.tagme_bank_back.domain.service.BankAccountService;
import com.tagme.tagme_bank_back.domain.service.CreditCardService;
import com.tagme.tagme_bank_back.usecase.CreditCardPaymentUseCase;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public class CreditCardPaymentUseCaseImpl implements CreditCardPaymentUseCase {
    private final AuthService authService;
    private final BankAccountService bankAccountService;
    private final CreditCardService creditCardService;


    public CreditCardPaymentUseCaseImpl(AuthService authService, BankAccountService bankAccountService,CreditCardService creditCardService) {
        this.authService = authService;
        this.bankAccountService = bankAccountService;
        this.creditCardService = creditCardService;
    }

    @Override
    @Transactional
    public void execute(CreditCardPaymentDto creditCardPaymentDto) {
        String username = creditCardPaymentDto.auth().username();
        String apiKey = creditCardPaymentDto.auth().apiKey();
        String ibanDestination = creditCardPaymentDto.transferDto().iban();
        CreditCard creditCardOrigin = creditCardPaymentDto.creditCard();
        BigDecimal paymentAmount = creditCardPaymentDto.payDto().amount();
        String creditCardNumber = creditCardOrigin.getNumber();
        String concept = creditCardPaymentDto.payDto().concept();

        // Validar tarjeta
        creditCardService.validateCreditCard(creditCardOrigin);

        // Comprobar credenciales
        authService.authorize(username, apiKey);

        // Comprobar usuario e iban
        bankAccountService.checkOwner(ibanDestination, username);

        // Recoger Iban cliente
        String ibanClient = bankAccountService.getIbanByCreditCardNumber(creditCardNumber);

        if(ibanClient.equals(ibanDestination)){
            throw new IllegalArgumentException("Las cuentas de origen y destino no pueden ser las mismas");
        }

        // Recoger saldo del Cliente
        BigDecimal clientBalance = bankAccountService.getBalanceByIban(ibanClient);
        checkAvaliableBalance(clientBalance, paymentAmount);

        // Añadir id a la tarjeta
        creditCardOrigin.setId(creditCardService.getIdByCreditCardNumber(creditCardNumber));

        // Realizar el pago
        BankAccount accountClient = bankAccountService.getByIban(ibanClient);
        BankAccount accountDestination = bankAccountService.getByIban(ibanDestination);

        bankAccountService.withdrawByCard(accountClient, paymentAmount, creditCardOrigin, concept);
        bankAccountService.deposit(accountDestination, paymentAmount, MovementOrigin.CARD, concept);
    }


    private void checkAvaliableBalance(BigDecimal clientBalance, BigDecimal paymentAmount) {
        if (clientBalance.subtract(paymentAmount).compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientBalanceException("Saldo insuficiente");
        }

    }
}
