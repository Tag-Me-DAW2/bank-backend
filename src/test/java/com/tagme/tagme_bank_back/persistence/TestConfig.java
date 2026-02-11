package com.tagme.tagme_bank_back.persistence;

import com.tagme.tagme_bank_back.persistence.dao.jpa.AuthJpaDao;
import com.tagme.tagme_bank_back.persistence.dao.jpa.BankAccountJpaDao;
import com.tagme.tagme_bank_back.persistence.dao.jpa.ClientJpaDao;
import com.tagme.tagme_bank_back.persistence.dao.jpa.CreditCardJpaDao;
import com.tagme.tagme_bank_back.persistence.dao.jpa.MovementJpaDao;
import com.tagme.tagme_bank_back.persistence.dao.jpa.impl.AuthJpaDaoImpl;
import com.tagme.tagme_bank_back.persistence.dao.jpa.impl.BankAccountJpaDaoImpl;
import com.tagme.tagme_bank_back.persistence.dao.jpa.impl.ClientJpaDaoImpl;
import com.tagme.tagme_bank_back.persistence.dao.jpa.impl.CreditCardJpaDaoImpl;
import com.tagme.tagme_bank_back.persistence.dao.jpa.impl.MovementJpaDaoImpl;
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

    @Bean
    AuthJpaDao authJpaDao() {
        return new AuthJpaDaoImpl();
    }

    @Bean
    MovementJpaDao movementJpaDao() {
        return new MovementJpaDaoImpl();
    }

    @Bean
    BankAccountJpaDao bankAccountJpaDao() {
        return new BankAccountJpaDaoImpl();
    }

    @Bean
    CreditCardJpaDao creditCardJpaDao() {
        return new CreditCardJpaDaoImpl();
    }
}
