package com.tagme.tagme_bank_back.persistence.dao.jpa;

import com.tagme.tagme_bank_back.domain.model.Client;

import java.util.Map;

public interface AuthJpaDao {
    Map<Client, String> login(Client client);
    void logout(String token);
    Long count();
}
