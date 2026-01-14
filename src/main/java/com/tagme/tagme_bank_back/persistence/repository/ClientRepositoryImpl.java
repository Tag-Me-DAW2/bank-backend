package com.tagme.tagme_bank_back.persistence.repository;

import com.tagme.tagme_bank_back.domain.model.Client;
import com.tagme.tagme_bank_back.domain.repository.ClientRepository;
import com.tagme.tagme_bank_back.persistence.dao.jpa.ClientJpaDao;

import java.util.Optional;

public class ClientRepositoryImpl implements ClientRepository {
    private final ClientJpaDao clientDao;

    public ClientRepositoryImpl(ClientJpaDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public Optional<Client> findByUsername(String username) {
        return clientDao.findByUsername(username);
    }
}
