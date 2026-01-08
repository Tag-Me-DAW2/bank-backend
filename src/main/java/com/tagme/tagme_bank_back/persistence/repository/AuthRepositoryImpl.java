package com.tagme.tagme_bank_back.persistence.repository;

import com.tagme.tagme_bank_back.domain.model.Client;
import com.tagme.tagme_bank_back.domain.repository.AuthRepository;
import com.tagme.tagme_bank_back.persistence.dao.jpa.AuthJpaDao;

import java.util.Map;

public class AuthRepositoryImpl implements AuthRepository {
    private final AuthJpaDao authJpaDao;

    public AuthRepositoryImpl(AuthJpaDao authJpaDao) {
        this.authJpaDao = authJpaDao;
    }

    @Override
    public Map<Client, String> login(Client client) {
        return authJpaDao.login(client);
    }

    @Override
    public void logout(String token) {
        authJpaDao.logout(token);
    }
}
