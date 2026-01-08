package com.tagme.tagme_bank_back.spring;

import com.tagme.tagme_bank_back.domain.repository.ClientRepository;
import com.tagme.tagme_bank_back.domain.service.ClientService;
import com.tagme.tagme_bank_back.domain.service.impl.ClientServiceImpl;
import com.tagme.tagme_bank_back.persistence.dao.jpa.ClientJpaDao;
import com.tagme.tagme_bank_back.persistence.dao.jpa.impl.ClientJpaDaoImpl;
import com.tagme.tagme_bank_back.persistence.repository.ClientRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    ClientJpaDao clientJpaDao() {
        return new ClientJpaDaoImpl();
    }

    @Bean
    ClientRepository clientRepository(ClientJpaDao clientJpaDao) {
        return new ClientRepositoryImpl(clientJpaDao);
    }

    @Bean
    ClientService clientService(ClientRepository clientRepository) {
        return new ClientServiceImpl(clientRepository);
    }
}
