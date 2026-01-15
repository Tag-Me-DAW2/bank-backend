package com.tagme.tagme_bank_back.persistence.dao.jpa;

import com.tagme.tagme_bank_back.domain.model.Client;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.ClientJpaEntity;

import java.util.Optional;

public interface ClientJpaDao extends GenericJpaDao<ClientJpaEntity> {
    Boolean existsByUsernameAndApiToken(String username, String apiKey);
    Optional<Client> findByUsername(String username);
    ClientJpaEntity insert(ClientJpaEntity clientJpaEntity);
    Boolean apiKeyExists(String apiKey);
}
