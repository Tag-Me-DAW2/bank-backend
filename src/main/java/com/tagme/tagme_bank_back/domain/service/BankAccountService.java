package com.tagme.tagme_bank_back.domain.service;

import com.tagme.tagme_bank_back.domain.model.BankAccount;

import java.util.List;

public interface BankAccountService {
    BankAccount getById(Long id);
    BankAccount getByIban(String iban);
    Boolean checkOwner(String iban, String username);
    List<BankAccount> getByClientId(Long clientId);
}
