package com.tagme.tagme_bank_back.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.math.BigDecimal;

public class BankAccount {
    private Long id;
    private String iban;
    private BigDecimal balance;
    private Client client;
    private List<CreditCard> creditCards;
    private List<Movement> movements;

    public BankAccount(Long id, String iban, BigDecimal balance, Client client, List<CreditCard> creditCards, List<Movement> movements) {
        this.id = id;
        this.iban = iban;
        this.balance = balance;
        this.client = client;
        this.creditCards = new ArrayList<>(creditCards);
        this.movements = new ArrayList<>(movements);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(List<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    public List<Movement> getMovements() {
        return movements;
    }

    public void setMovements(List<Movement> movements) {
        this.movements = movements;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void withdrawByCard(BigDecimal amount, CreditCard card, String concept) {
        Movement movement = new Movement(
                null,
                MovementType.WITHDRAWAL,
                MovementOrigin.CARD,
                card,
                LocalDate.now(),
                amount,
                concept
        );

        this.movements.add(movement);
        this.balance = this.balance.subtract(amount);
    }

    public void withdrawByTransfer(BigDecimal amount, String concept) {
        Movement movement = new Movement(
                null,
                MovementType.WITHDRAWAL,
                MovementOrigin.TRANSFER,
                null,
                LocalDate.now(),
                amount,
                concept
        );

        this.movements.add(movement);
        this.balance = this.balance.subtract(amount);
    }
    public void deposit(BigDecimal amount, MovementOrigin origin, String concept) {
        Movement movement = new Movement(
                null,
                MovementType.DEPOSIT,
                origin,
                null,
                LocalDate.now(),
                amount,
                concept
        );

        this.movements.add(movement);
        this.balance = this.balance.add(amount);
    }


    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", iban='" + iban + '\'' +
                ", balance=" + balance +
                ", client=" + client +
                ", creditCards=" + creditCards +
                ", movements=" + movements +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return Objects.equals(id, that.id) && Objects.equals(iban, that.iban) && Objects.equals(balance, that.balance) && Objects.equals(client, that.client) && Objects.equals(creditCards, that.creditCards) && Objects.equals(movements, that.movements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, iban, balance, client, creditCards, movements);
    }
}
