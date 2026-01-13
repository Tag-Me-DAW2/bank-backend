package com.tagme.tagme_bank_back.spring;

import com.tagme.tagme_bank_back.domain.repository.*;
import com.tagme.tagme_bank_back.domain.service.*;
import com.tagme.tagme_bank_back.domain.service.impl.*;
import com.tagme.tagme_bank_back.persistence.dao.jpa.*;
import com.tagme.tagme_bank_back.persistence.dao.jpa.impl.*;
import com.tagme.tagme_bank_back.persistence.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    // Client Beans
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

    // Auth Beans
    @Bean
    AuthJpaDao authJpaDao() {return new AuthJpaDaoImpl();
    }

    @Bean
    AuthRepository authRepository(AuthJpaDao authJpaDao) {
        return new AuthRepositoryImpl(authJpaDao);
    }

    @Bean
    AuthService authService(AuthRepository authRepository, ClientRepository clientRepository) {
        return new AuthServiceImpl(clientRepository, authRepository);
    }

    // Credit Card Beans
    @Bean
    CreditCardJpaDao creditCardJpaDao() {return new CreditCardJpaDaoImpl();}

    @Bean
    CreditCardRepository creditCardRepository(CreditCardJpaDao creditCardJpaDao) {
        return new CreditCardRepositoryImpl(creditCardJpaDao);
    }

    @Bean
    CreditCardService creditCardService(CreditCardRepository creditCardRepository) {
        return new CreditCardServiceImpl(creditCardRepository);
    }

    // Movements Beans
    @Bean
    MovementJpaDao movementJpaDao() {
        return new MovementJpaDaoImpl();
    }

    @Bean
    MovementRepository movementRepository(MovementJpaDao movementJpaDao) {
        return new MovementRepositoryImpl(movementJpaDao);
    }

    @Bean
    MovementService movementService(MovementRepository movementRepository) {
        return new MovementServiceImpl(movementRepository);
    }

    // BankAccount Beans
    @Bean
    BankAccountJpaDao bankAccountJpaDao() {
        return new BankAccountJpaDaoImpl();
    }

    @Bean
    BankAccountRepository bankAccountRepository(BankAccountJpaDao bankAccountJpaDao) {
        return new BankAccountRepositoryImpl(bankAccountJpaDao);
    }

    @Bean
    BankAccountService bankAccountService(BankAccountRepository bankAccountRepository) {
        return new BankAccountServiceImpl(bankAccountRepository);
    }

}
