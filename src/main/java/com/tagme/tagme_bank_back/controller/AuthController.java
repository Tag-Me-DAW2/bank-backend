package com.tagme.tagme_bank_back.controller;

import com.tagme.tagme_bank_back.controller.mapper.ClientMapper;
import com.tagme.tagme_bank_back.controller.webModel.request.LoginRequest;
import com.tagme.tagme_bank_back.controller.webModel.response.ClientResponse;
import com.tagme.tagme_bank_back.controller.webModel.response.LoginResponse;
import com.tagme.tagme_bank_back.domain.model.Client;
import com.tagme.tagme_bank_back.domain.service.AuthService;
import com.tagme.tagme_bank_back.domain.validation.DtoValidator;
import com.tagme.tagme_bank_back.web.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        DtoValidator.validate(loginRequest);

        Map<Client, String> authResult = authService.authenticate(loginRequest.username(), loginRequest.password());
        ClientResponse clientResponse = ClientMapper.fromClientToClientResponse(authResult.keySet().iterator().next());
        String token = authResult.values().iterator().next();

        LoginResponse loginResponse = new LoginResponse(clientResponse, token);

        return new ResponseEntity<>(loginResponse,HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token.substring(7));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
