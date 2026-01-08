package com.tagme.tagme_bank_back.persistence.dao.jpa.impl;

import com.tagme.tagme_bank_back.domain.model.Client;
import com.tagme.tagme_bank_back.persistence.dao.jpa.ClientJpaDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Optional;

public class ClientJpaDaoImpl implements ClientJpaDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Boolean> existsByUsernameAndApiToken(String username, String apiKey) {
        String query = "SELECT COUNT(c) FROM ClientJpaEntity c WHERE c.username = :username AND c.apiKey = :apiKey";
        Long count = entityManager.createQuery(query, Long.class)
                .setParameter("username", username)
                .setParameter("apiKey", apiKey)
                .getSingleResult();
        return Optional.of(count > 0);
    }

    @Override
    public Optional<Client> getByUsername(String username) {
        String query = "SELECT c FROM ClientJpaEntity c WHERE c.username = :username";
        Client client = entityManager.createQuery(query, Client.class)
                .setParameter("username", username)
                .getSingleResult();
        return Optional.ofNullable(client);
    }
}
