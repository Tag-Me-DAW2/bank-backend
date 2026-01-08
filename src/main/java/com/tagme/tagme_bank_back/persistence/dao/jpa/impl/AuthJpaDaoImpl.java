package com.tagme.tagme_bank_back.persistence.dao.jpa.impl;

import com.tagme.tagme_bank_back.domain.exception.NotFoundException;
import com.tagme.tagme_bank_back.domain.model.Client;
import com.tagme.tagme_bank_back.persistence.dao.jpa.AuthJpaDao;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.ClientJpaEntity;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.SessionJpaEntity;
import com.tagme.tagme_bank_back.web.util.JwtUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.Map;

public class AuthJpaDaoImpl implements AuthJpaDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Map<Client, String> login(Client client) {
        ClientJpaEntity clientJpaEntity = entityManager.find(ClientJpaEntity.class, client.getId());
        if (clientJpaEntity == null) {
            throw new NotFoundException("Client not found");
        }

        // Borrar sesiones antiguas

        entityManager.createQuery("DELETE FROM SessionJpaEntity s WHERE s.client.id = :clientId")
                .setParameter("clientId", client.getId())
                .executeUpdate();

        String token = JwtUtil.generateToken(client);

        LocalDateTime createdAt = LocalDateTime.now();

        SessionJpaEntity sessionJpaEntity = new SessionJpaEntity(clientJpaEntity, token, createdAt);
        entityManager.persist(sessionJpaEntity);

        return Map.of(client, token);
    }

    @Override
    public void logout(String token) {
        SessionJpaEntity sessionJpaEntity = entityManager.createQuery(
                        "SELECT s FROM SessionJpaEntity s WHERE s.token = :token", SessionJpaEntity.class)
                .setParameter("token", token)
                .getResultStream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Session not found for token: " + token));

        entityManager.remove(sessionJpaEntity);
    }

    @Override
    public Long count() {
        return entityManager.createQuery("SELECT COUNT(s) FROM SessionJpaEntity s", Long.class)
                .getSingleResult();
    }
}
