package com.tagme.tagme_bank_back.domain.service;

import com.tagme.tagme_bank_back.domain.model.Client;

import java.util.Map;

public interface AuthService {
    Map<Client,String> authenticate(String username, String password);
    Boolean authorize(String username, String token);
    void logout(String token);
}
