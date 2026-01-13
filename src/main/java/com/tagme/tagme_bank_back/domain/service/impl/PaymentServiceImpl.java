package com.tagme.tagme_bank_back.domain.service.impl;

import com.tagme.tagme_bank_back.domain.dto.CreditCardPaymentDto;
import com.tagme.tagme_bank_back.domain.exception.InsufficientBalanceException;
import com.tagme.tagme_bank_back.domain.exception.InvalidCredentialsException;
import com.tagme.tagme_bank_back.domain.exception.NotFoundException;
import com.tagme.tagme_bank_back.domain.model.BankAccount;
import com.tagme.tagme_bank_back.domain.model.Movement;
import com.tagme.tagme_bank_back.domain.model.MovementOrigin;
import com.tagme.tagme_bank_back.domain.model.MovementType;
import com.tagme.tagme_bank_back.domain.repository.BankAccountRepository;
import com.tagme.tagme_bank_back.domain.repository.ClientRepository;
import com.tagme.tagme_bank_back.domain.repository.CreditCardRepository;
import com.tagme.tagme_bank_back.domain.repository.MovementRepository;
import com.tagme.tagme_bank_back.domain.service.PaymentService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentServiceImpl implements PaymentService {
    private final ClientRepository clientRepository;
    private final BankAccountRepository bankAccountRepository;
    private final CreditCardRepository creditCardRepository;
    private final MovementRepository movementRepository;

    public PaymentServiceImpl(ClientRepository clientRepository, BankAccountRepository bankAccountRepository, CreditCardRepository creditCardRepository, MovementRepository movementRepository) {
        this.creditCardRepository = creditCardRepository;
        this.clientRepository = clientRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.movementRepository = movementRepository;
    }

    @Override
    @Transactional
    public BigDecimal processCreditCardPayment(CreditCardPaymentDto creditCardPaymentDto) {
        // Comprobar credenciales
        checkAuthCredentials(creditCardPaymentDto);

        // Comprobar usuario e iban
        checkAccountOwnership(creditCardPaymentDto);

        // Recoger Iban cliente
        String ibanClient = getIbanClient(creditCardPaymentDto);

        // Recoger id de la tarjeta
        creditCardPaymentDto.creditCard().setId(getCreditCardId(creditCardPaymentDto));

        // Recoger saldo del Cliente
        BigDecimal clientBalance = bankAccountRepository.getBalanceByIban(ibanClient);
        BigDecimal paymentAmount = creditCardPaymentDto.payDto().amount();
        checkAvaliableBalance(clientBalance, paymentAmount);

        // Realizar el pago
        BankAccount bankAccountDestination = getBankAccount(creditCardPaymentDto.transferDto().iban());
        BankAccount bankAccountClient = getBankAccount(ibanClient);

        Movement movementDestination = saveMovement(MovementType.DEPOSIT, MovementOrigin.CARD, creditCardPaymentDto, bankAccountDestination.getId());
        Movement movementOrigin = saveMovement(MovementType.WITHDRAWAL, MovementOrigin.CARD, creditCardPaymentDto, bankAccountClient.getId());

        bankAccountDestination.getMovements().add(movementDestination);
        bankAccountClient.getMovements().add(movementOrigin);

        // Devolver el nuevo saldo
        return setNewBalances(bankAccountDestination, bankAccountClient, paymentAmount);
    }

    private void checkAuthCredentials(CreditCardPaymentDto creditCardPaymentDto) {
        String username = creditCardPaymentDto.auth().username();
        String apiKey = creditCardPaymentDto.auth().apiKey();

        if (!clientRepository.existsByUsernameAndApiToken(username, apiKey)) {
            throw new InvalidCredentialsException("Invalid username or API key");
        }
    }

    private void checkAccountOwnership(CreditCardPaymentDto creditCardPaymentDto) {
        String iban = creditCardPaymentDto.transferDto().iban();
        String username = creditCardPaymentDto.auth().username();

        if (!bankAccountRepository.existsByIbanAndClientUsername(iban, username)) {
            throw new InvalidCredentialsException("The IBAN does not belong to the authenticated user");
        }
    }

    private void checkAvaliableBalance(BigDecimal clientBalance, BigDecimal paymentAmount) {
        if (clientBalance.subtract(paymentAmount).compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

    }

    private String getIbanClient(CreditCardPaymentDto creditCardPaymentDto) {
        return creditCardRepository.getIbanByCreditCard(creditCardPaymentDto.creditCard());
    }

    private Long getCreditCardId(CreditCardPaymentDto creditCardPaymentDto) {
        return creditCardRepository.getIdByCreditCardNumber(creditCardPaymentDto.creditCard().getNumber())
                .orElseThrow(() -> new NotFoundException("Credit card not found"));
    }

    private BankAccount getBankAccount(String iban) {
        return bankAccountRepository.findByIban(iban)
                .orElseThrow(() -> new NotFoundException("Bank account not found"));
    }

    private Movement saveMovement(MovementType movementType, MovementOrigin origin, CreditCardPaymentDto creditCardPaymentDto, Long bankAccountId) {
        Movement movementDestination = new Movement(
                null,
                movementType,
                origin,
                creditCardPaymentDto.creditCard(),
                LocalDate.now(),
                creditCardPaymentDto.payDto().amount(),
                creditCardPaymentDto.payDto().concept()
        );

        Movement movementSaved =  movementRepository.save(movementDestination, bankAccountId);

        if (movementSaved.getId() == null) {
            throw new RuntimeException("Error saving movement");
        }

        return movementSaved;
    }

    private BigDecimal setNewBalances(BankAccount bankAccountDestination, BankAccount bankAccountClient, BigDecimal paymentAmount) {
        bankAccountDestination.setBalance(bankAccountDestination.getBalance().add(paymentAmount));
        bankAccountClient.setBalance(bankAccountClient.getBalance().subtract(paymentAmount));

        bankAccountRepository.save(bankAccountDestination);
        bankAccountRepository.save(bankAccountClient);

        return bankAccountClient.getBalance();
    }
}
