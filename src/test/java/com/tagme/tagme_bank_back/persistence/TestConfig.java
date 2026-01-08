package com.tagme.tagme_bank_back.persistence;

import com.tagme.tagme_bank_back.persistence.dao.jpa.ClientJpaDao;
import com.tagme.tagme_bank_back.persistence.dao.jpa.impl.ClientJpaDaoImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@TestConfiguration
@EnableJpaRepositories(basePackages = "com.tagme.tagme_bank_back.persistence.dao.jpa")
@EntityScan(basePackages = "com.tagme.tagme_bank_back.persistence.dao.jpa.entity")
public class TestConfig {
    @Bean
    ClientJpaDao clientJpaDao() {
        return new ClientJpaDaoImpl();
    }
}
