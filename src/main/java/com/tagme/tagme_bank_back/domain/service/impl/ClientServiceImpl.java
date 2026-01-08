package com.tagme.tagme_bank_back.domain.service.impl;

import com.tagme.tagme_bank_back.domain.exception.InvalidCredentialsException;
import com.tagme.tagme_bank_back.domain.exception.NotFoundException;
import com.tagme.tagme_bank_back.domain.model.Client;
import com.tagme.tagme_bank_back.domain.repository.ClientRepository;
import com.tagme.tagme_bank_back.domain.service.ClientService;

public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Boolean checkCredentials(String username, String apiKey) {
        return clientRepository.existsByUsernameAndApiToken(username, apiKey).orElseThrow(() -> new InvalidCredentialsException("Invalid username or API token"));
    }

    @Override
    public Client getClientByUsername(String username) {
        return clientRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Client not found with username: " + username));
    }
}
