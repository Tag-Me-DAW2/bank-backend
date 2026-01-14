package com.tagme.tagme_bank_back.controller;

import com.tagme.tagme_bank_back.controller.mapper.ClientMapper;
import com.tagme.tagme_bank_back.controller.webModel.response.ClientResponse;
import com.tagme.tagme_bank_back.domain.validation.DtoValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tagme.tagme_bank_back.controller.webModel.request.CredentialsRequest;
import com.tagme.tagme_bank_back.domain.service.ClientService;

@RestController
@RequestMapping("/clients")
@CrossOrigin("*")

public class ClientController {
    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    // Borrar, ahora pertenece a authcontroller

//    @PostMapping
//    public ResponseEntity<Void> checkClient(@RequestBody CredentialsRequest credentials) {
//        DtoValidator.validate(credentials);
//
//        if(!clientService.checkCredentials(credentials.username(), credentials.apiKey())) {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}