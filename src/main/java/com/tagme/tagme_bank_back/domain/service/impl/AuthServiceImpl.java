package com.tagme.tagme_bank_back.domain.service.impl;

import com.tagme.tagme_bank_back.domain.exception.InvalidCredentialsException;
import com.tagme.tagme_bank_back.domain.exception.NotFoundException;
import com.tagme.tagme_bank_back.domain.model.Client;
import com.tagme.tagme_bank_back.domain.repository.AuthRepository;
import com.tagme.tagme_bank_back.domain.repository.ClientRepository;
import com.tagme.tagme_bank_back.domain.service.AuthService;
import com.tagme.tagme_bank_back.domain.util.Password4jUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public class AuthServiceImpl implements AuthService {
    private final ClientRepository clientRepository;
    private final AuthRepository authRepository;

    public AuthServiceImpl(ClientRepository clientRepository, AuthRepository authRepository) {
        this.clientRepository = clientRepository;
        this.authRepository = authRepository;
    }

    @Override
    @Transactional
    public Map<Client, String> authenticate(String username, String password) {
        Client client = clientRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Cliente no encontrado con nombre de usuario: " + username));

        boolean isPasswordValid = Password4jUtil.verifyPassword(password, client.getPassword());
        if(!isPasswordValid) throw new InvalidCredentialsException("Contraseña inválida para el usuario: " + username);

        return authRepository.login(client);
    }

    @Override
    @Transactional
    public void logout(String token) {
        if (token == null ||  token.isBlank()) {
            throw new InvalidCredentialsException("Token inválido");
        }

        authRepository.logout(token);
    }

    @Override
    public Boolean authorize(String username, String token) {
        if (!authRepository.isAuthorized(username, token)) {
            throw new InvalidCredentialsException("Acceso no autorizado para el usuario: " + username);
        }
        return true;
    }

    @Override
    public Boolean isApiKeyValid(String apiKey) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new RuntimeException("API key inválida");
        }

        if (!authRepository.isApiKeyValid(apiKey)) {
            throw new InvalidCredentialsException("Clave API inválida");
        } else {
            return true;
        }
    }
}
