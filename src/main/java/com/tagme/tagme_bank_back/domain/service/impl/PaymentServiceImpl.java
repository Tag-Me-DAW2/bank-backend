package com.tagme.tagme_bank_back.domain.service.impl;

import com.tagme.tagme_bank_back.domain.dto.CreditCardPaymentDto;
import com.tagme.tagme_bank_back.domain.exception.InvalidCredentialsException;
import com.tagme.tagme_bank_back.domain.repository.BankAccountRepository;
import com.tagme.tagme_bank_back.domain.repository.ClientRepository;
import com.tagme.tagme_bank_back.domain.repository.CreditCardRepository;
import com.tagme.tagme_bank_back.domain.service.BankAccountService;
import com.tagme.tagme_bank_back.domain.service.PaymentService;

import java.math.BigDecimal;

public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final ClientRepository clientRepository;
    private final BankAccountRepository bankAccountRepository;
    private final CreditCardRepository creditCardRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, ClientRepository clientRepository, BankAccountRepository bankAccountRepository, CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
        this.clientRepository = clientRepository;
        this.paymentRepository = paymentRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public BigDecimal processCreditCardPayment(CreditCardPaymentDto creditCardPaymentDto) {
        // Comprobar credenciales
        String username = creditCardPaymentDto.auth().username();
        String apiKey = creditCardPaymentDto.auth().apiKey();

        if (!clientRepository.existsByUsernameAndApiToken(username, apiKey)) {
            throw new InvalidCredentialsException("Invalid username or API key");
        }

        // Comprobar usuario y iban
        String iban = creditCardPaymentDto.transferDto().iban();
        if (!bankAccountRepository.existsByIbanAndClientUsername(iban, username)) {
            throw new InvalidCredentialsException("The IBAN does not belong to the authenticated user");
        }

        // Recoger Iban cliente
        String ibanClient = ;
    }
}
