package com.tagme.tagme_bank_back.persistence.dao.jpa.impl;

import com.tagme.tagme_bank_back.domain.model.Client;
import com.tagme.tagme_bank_back.persistence.dao.jpa.ClientJpaDao;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.ClientJpaEntity;
import com.tagme.tagme_bank_back.persistence.mapper.ClientMapper;
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
    public Optional<Client> findByUsername(String username) {
        String query = "SELECT c FROM ClientJpaEntity c WHERE c.username = :username";
        try {
            ClientJpaEntity clientJpaEntity = entityManager.createQuery(query, ClientJpaEntity.class)
                    .setParameter("username", username)
                    .getSingleResult();

            Client client = ClientMapper.fromClientJpaEntityToClient(clientJpaEntity);

            return Optional.ofNullable(client);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Long count() {
        return entityManager.createQuery("SELECT COUNT(c) FROM ClientJpaEntity c", Long.class).getSingleResult();
    }

    @Override
    public Optional<ClientJpaEntity> findById(Long id) {
        return Optional.ofNullable(entityManager.find(ClientJpaEntity.class, id));
    }
}
