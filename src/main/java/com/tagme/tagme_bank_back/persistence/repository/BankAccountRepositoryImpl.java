package com.tagme.tagme_bank_back.persistence.repository;

import com.tagme.tagme_bank_back.domain.model.BankAccount;
import com.tagme.tagme_bank_back.domain.repository.BankAccountRepository;
import com.tagme.tagme_bank_back.persistence.dao.jpa.BankAccountJpaDao;
import com.tagme.tagme_bank_back.persistence.mapper.BankAccountMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.stream;

public class BankAccountRepositoryImpl implements BankAccountRepository {
    private final BankAccountJpaDao bankAccountJpaDao;

    public BankAccountRepositoryImpl(BankAccountJpaDao bankAccountJpaDao) {
        this.bankAccountJpaDao = bankAccountJpaDao;
    }

    @Override
    public Optional<BankAccount> findById(Long id) {
        return bankAccountJpaDao.findById(id).map(BankAccountMapper::fromBankAccountJpaEntityToBankAccount);
    }

    @Override
    public Optional<BankAccount> findByIban(String iban) {
        return bankAccountJpaDao.findByIban(iban).map(BankAccountMapper::fromBankAccountJpaEntityToBankAccount);
    }

    @Override
    public Boolean existsByIbanAndClientUsername(String iban, String username) {
        return bankAccountJpaDao.existsByIbanAndClientUsername(iban, username);
    }

    @Override
    public List<BankAccount> findByClientId(Long clientId) {
        return bankAccountJpaDao.findByClientId(clientId)
                .stream()
                .map(BankAccountMapper::fromBankAccountJpaEntityToBankAccount)
                .toList();
    }

    @Override
    public Optional<BigDecimal> findBalanceByIban(String iban) {
        return bankAccountJpaDao.findBalanceByIban(iban);
    }

    @Override
    public BankAccount save(BankAccount bankAccount) {
        if (bankAccount.getId() == null) {
            return BankAccountMapper.fromBankAccountJpaEntityToBankAccount(
                    bankAccountJpaDao.insert(BankAccountMapper.fromBankAccountToBankAccountJpaEntity(bankAccount))
            );
        } else {
            return BankAccountMapper.fromBankAccountJpaEntityToBankAccount(
                    bankAccountJpaDao.update(BankAccountMapper.fromBankAccountToBankAccountJpaEntity(bankAccount))
            );
        }
    }

    @Override
    public Optional<String> findIbanByCreditCardNumber(String creditCardNumber) {
        return bankAccountJpaDao.findIbanByCreditCardNumber(creditCardNumber);
    }
}
