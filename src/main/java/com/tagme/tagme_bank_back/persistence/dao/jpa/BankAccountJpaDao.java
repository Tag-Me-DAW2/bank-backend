package com.tagme.tagme_bank_back.persistence.dao.jpa;

import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.BankAccountJpaEntity;

import java.util.List;
import java.util.Optional;

public interface BankAccountJpaDao extends GenericJpaDao<BankAccountJpaEntity> {
    Optional<BankAccountJpaEntity> findByIban(String iban);
    Boolean existsByIbanAndClientUsername(String iban, String username);
    List<BankAccountJpaEntity> findByClientId(Long clientId);
}
