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
}
