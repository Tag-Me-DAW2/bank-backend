package com.tagme.tagme_bank_back.domain.service;


import com.tagme.tagme_bank_back.domain.model.Client;

public interface ClientService {
    Client getClientByUsername(String username);
}
