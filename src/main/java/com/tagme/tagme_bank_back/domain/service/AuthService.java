package com.tagme.tagme_bank_back.domain.service;

import com.tagme.tagme_bank_back.domain.model.Client;

import java.util.Map;

public interface AuthService {
    Map<Client,String> authenticate(String username, String password);
    void logout(String token);
}
