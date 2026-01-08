package com.tagme.tagme_bank_back.domain.repository;

import com.tagme.tagme_bank_back.domain.model.Client;

import java.util.Optional;

public interface ClientRepository {
    Optional<Boolean> existsByUsernameAndApiToken(String username, String apiKey);
    Optional<Client> getByUsername(String username);
}
