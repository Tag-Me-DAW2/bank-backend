package com.tagme.tagme_bank_back.domain.repository;

import com.tagme.tagme_bank_back.domain.model.BankAccount;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepository {
    Optional<BankAccount> findById(Long id);
    Optional<BankAccount> findByIban(String iban);
    Boolean existsByIbanAndClientUsername(String iban, String username);
    List<BankAccount> findByClientId(Long clientId);
}
