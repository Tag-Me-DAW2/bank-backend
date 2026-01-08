package com.tagme.tagme_bank_back.controller.mapper;

import com.tagme.tagme_bank_back.controller.webModel.response.ClientResponse;
import com.tagme.tagme_bank_back.domain.model.Client;

public class ClientMapper {
    public static ClientResponse fromClientToClientResponse(Client client) {
        if (client.getId() == null) {
            return null;
        }

        return new ClientResponse(
                client.getId(),
                client.getUsername(),
                client.getName(),
                client.getLastName1(),
                client.getLastName2(),
                client.getDni()
        );
    }
}
