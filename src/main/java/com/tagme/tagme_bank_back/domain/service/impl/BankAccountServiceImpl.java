package com.tagme.tagme_bank_back.domain.service.impl;

import com.tagme.tagme_bank_back.domain.exception.NotFoundException;
import com.tagme.tagme_bank_back.domain.model.*;
import com.tagme.tagme_bank_back.domain.repository.BankAccountRepository;
import com.tagme.tagme_bank_back.domain.repository.MovementRepository;
import com.tagme.tagme_bank_back.domain.service.BankAccountService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public BankAccount getById(Long id) {
        if (id == null) {
            throw new RuntimeException("El ID de la cuenta bancaria no puede ser nulo");
        }

        return bankAccountRepository.findById(id).orElseThrow(() -> new NotFoundException("Cuenta bancaria no encontrada"));
    }

    @Override
    public BankAccount getByIban(String iban) {
        if (iban == null || iban.isBlank()) {
            throw new RuntimeException("El IBAN no puede ser nulo o vacío");
        }
        return bankAccountRepository.findByIban(normalizeIban(iban)).orElseThrow(() -> new NotFoundException("Cuenta bancaria no encontrada"));
    }

    @Override
    public Boolean checkOwner(String iban, String username) {
        if (iban == null || iban.isBlank()) {
            throw new RuntimeException("El IBAN no puede ser nulo o vacío");
        }
        if (username == null || username.isBlank()) {
            throw new RuntimeException("El nombre de usuario no puede ser nulo o vacío");
        }

        if(!bankAccountRepository.existsByIbanAndClientUsername(iban, username)) {
            throw new NotFoundException("Cuenta bancaria no encontrada para el IBAN y usuario especificados");
        }

        return true;
    }

    @Override
    public List<BankAccount> getByClientId(Long clientId) {
        if (clientId == null) {
            throw new RuntimeException("El ID del cliente no puede ser nulo");
        }

        return bankAccountRepository.findByClientId(clientId);
    }

    @Override
    public BigDecimal getBalanceByIban(String iban) {
        if (iban == null || iban.isBlank()) {
            throw new RuntimeException("El IBAN no puede ser nulo o vacío");
        }

        return bankAccountRepository.findBalanceByIban(iban).orElseThrow(() -> new NotFoundException("Cuenta bancaria no encontrada"));
    }

    @Override
    public String getIbanByCreditCardNumber(String creditCardNumber) {
        if (creditCardNumber == null || creditCardNumber.isBlank()) {
            throw new RuntimeException("El número de tarjeta de crédito no puede ser nulo o vacío");
        }

        return bankAccountRepository.findIbanByCreditCardNumber(creditCardNumber)
                .orElseThrow(() -> new NotFoundException("Cuenta bancaria no encontrada para el número de tarjeta especificado"));
    }

    @Override
    @Transactional
    public void withdrawByCard(BankAccount account, BigDecimal amount, CreditCard creditCard, String concept) {
        account.withdrawByCard(amount, creditCard, concept);
        bankAccountRepository.save(account);
    }

    @Override
    @Transactional
    public void withdrawByTransfer(BankAccount account, BigDecimal amount, String concept) {
        account.withdrawByTransfer(amount, concept);
        bankAccountRepository.save(account);
    }

    @Override
    @Transactional
    public void deposit(BankAccount account, BigDecimal amount, MovementOrigin movementOrigin, String concept) {
        account.deposit(amount, movementOrigin, concept);
        bankAccountRepository.save(account);
    }


    private String normalizeIban(String iban) {
        return iban.replaceAll("\\s+", "").trim().toUpperCase();
    }
}
