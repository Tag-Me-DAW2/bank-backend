package com.tagme.tagme_bank_back.domain.service;


import com.tagme.tagme_bank_back.domain.model.Client;

public interface ClientService {
    Boolean checkCredentials(String username, String apiKey);
    Client getClientByUsername(String username);
}
