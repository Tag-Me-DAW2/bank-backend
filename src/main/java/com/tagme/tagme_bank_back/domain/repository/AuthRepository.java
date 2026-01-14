package com.tagme.tagme_bank_back.domain.repository;

import com.tagme.tagme_bank_back.domain.model.Client;

import java.util.Map;

public interface AuthRepository {
    Map<Client, String> login(Client client);
    void logout(String token);
    Boolean isAuthorized(String username, String token);
}
