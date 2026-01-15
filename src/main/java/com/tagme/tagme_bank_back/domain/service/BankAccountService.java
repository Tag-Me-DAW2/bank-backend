package com.tagme.tagme_bank_back.domain.service;

import com.tagme.tagme_bank_back.domain.model.BankAccount;
import com.tagme.tagme_bank_back.domain.model.CreditCard;
import com.tagme.tagme_bank_back.domain.model.MovementOrigin;

import java.math.BigDecimal;
import java.util.List;

public interface BankAccountService {
    BankAccount getById(Long id);
    BankAccount getByIban(String iban);
    Boolean checkOwner(String iban, String username);
    List<BankAccount> getByClientId(Long clientId);
    BigDecimal getBalanceByIban(String iban);
    String getIbanByCreditCardNumber(String creditCardNumber);
    void withdrawByCard(BankAccount account, BigDecimal amount, CreditCard creditCard, String concept);
    void withdrawByTransfer(BankAccount account, BigDecimal amount, String concept);
    void deposit(BankAccount account, BigDecimal amount, MovementOrigin movementOrigin, String concept);
 }
