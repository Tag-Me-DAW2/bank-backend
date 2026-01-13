package com.tagme.tagme_bank_back.domain.service.impl;

import com.tagme.tagme_bank_back.domain.exception.NotFoundException;
import com.tagme.tagme_bank_back.domain.model.BankAccount;
import com.tagme.tagme_bank_back.domain.repository.BankAccountRepository;
import com.tagme.tagme_bank_back.domain.service.BankAccountService;

import java.util.List;

public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public BankAccount getById(Long id) {
        if (id == null) {
            throw new RuntimeException("Bank account ID cannot be null");
        }

        return bankAccountRepository.findById(id).orElseThrow(() -> new NotFoundException("Bank account not found"));
    }

    @Override
    public BankAccount getByIban(String iban) {
        if (iban == null || iban.isBlank()) {
            throw new RuntimeException("IBAN cannot be null or blank");
        }

        return bankAccountRepository.findByIban(iban).orElseThrow(() -> new NotFoundException("Bank account not found"));
    }

    @Override
    public Boolean checkOwner(String iban, String username) {
        if (iban == null || iban.isBlank()) {
            throw new RuntimeException("IBAN cannot be null or blank");
        }
        if (username == null || username.isBlank()) {
            throw new RuntimeException("Username cannot be null or blank");
        }

        return bankAccountRepository.existsByIbanAndClientUsername(iban, username);
    }

    @Override
    public List<BankAccount> getByClientId(Long clientId) {
        if (clientId == null) {
            throw new RuntimeException("Client ID cannot be null");
        }

        return bankAccountRepository.findByClientId(clientId);
    }
}
