package com.tagme.tagme_bank_back.persistence.repository;

import com.tagme.tagme_bank_back.domain.model.Client;
import com.tagme.tagme_bank_back.domain.repository.AuthRepository;
import com.tagme.tagme_bank_back.persistence.dao.jpa.AuthJpaDao;
import com.tagme.tagme_bank_back.persistence.dao.jpa.ClientJpaDao;

import java.util.Map;

public class AuthRepositoryImpl implements AuthRepository {
    private final AuthJpaDao authJpaDao;
    private final ClientJpaDao clientJpaDao;

    public AuthRepositoryImpl(AuthJpaDao authJpaDao, ClientJpaDao clientJpaDao) {
        this.authJpaDao = authJpaDao;
        this.clientJpaDao = clientJpaDao;
    }

    @Override
    public Map<Client, String> login(Client client) {
        return authJpaDao.login(client);
    }

    @Override
    public void logout(String token) {
        authJpaDao.logout(token);
    }

    @Override
    public Boolean isAuthorized(String username, String token) {
        return clientJpaDao.existsByUsernameAndApiToken(username, token);
    }
}
