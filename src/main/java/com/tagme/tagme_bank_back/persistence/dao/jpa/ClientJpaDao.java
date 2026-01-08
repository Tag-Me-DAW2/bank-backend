package com.tagme.tagme_bank_back.persistence.dao.jpa;

import com.tagme.tagme_bank_back.domain.model.Client;

import java.util.Optional;

public interface ClientJpaDao {
    Optional<Boolean> existsByUsernameAndApiToken(String username, String apiKey);

    Optional<Client> getByUsername(String username);
}
