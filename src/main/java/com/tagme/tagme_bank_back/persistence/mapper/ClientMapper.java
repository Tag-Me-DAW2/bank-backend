package com.tagme.tagme_bank_back.persistence.mapper;

import com.tagme.tagme_bank_back.domain.model.Client;
import com.tagme.tagme_bank_back.persistence.dao.jpa.entity.ClientJpaEntity;

public class ClientMapper {
    public static Client fromClientJpaEntityToClient(ClientJpaEntity clientJpaEntity) {
        if (clientJpaEntity.getId() == null) {
            return null;
        }

        return new Client(
                clientJpaEntity.getId(),
                clientJpaEntity.getUsername(),
                clientJpaEntity.getPassword(),
                clientJpaEntity.getName(),
                clientJpaEntity.getLastName1(),
                clientJpaEntity.getLastName2(),
                clientJpaEntity.getDni(),
                clientJpaEntity.getApiKey()
        );
    }

    public static ClientJpaEntity fromClientToClientJpaEntity(Client client) {
        if (client == null) {
            return null;
        }

        ClientJpaEntity clientJpaEntity = new ClientJpaEntity();
        clientJpaEntity.setId(client.getId());
        clientJpaEntity.setUsername(client.getUsername());
        clientJpaEntity.setPassword(client.getPassword());
        clientJpaEntity.setName(client.getName());
        clientJpaEntity.setLastName1(client.getLastName1());
        clientJpaEntity.setLastName2(client.getLastName2());
        clientJpaEntity.setDni(client.getDni());
        clientJpaEntity.setApiKey(client.getApiKey());

        return clientJpaEntity;
    }
}
