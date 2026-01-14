package com.tagme.tagme_bank_back.persistence.dao.jpa.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "tb_bank_accounts")
public class BankAccountJpaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String iban;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private ClientJpaEntity client;

    @OneToMany(
            mappedBy = "bankAccount",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CreditCardJpaEntity> creditCards = new ArrayList<>();

    @OneToMany(
            mappedBy = "bankAccount",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MovementJpaEntity> movements = new ArrayList<>();

    // --- helpers opcionales ---
    public void addCreditCard(CreditCardJpaEntity card) {
        creditCards.add(card);
        card.setBankAccount(this);
    }

    public void addMovement(MovementJpaEntity movement) {
        movements.add(movement);
        movement.setBankAccount(this);
    }

    public BankAccountJpaEntity() {
    }

    public BankAccountJpaEntity(Long id, String iban, BigDecimal balance, ClientJpaEntity client, List<CreditCardJpaEntity> creditCards, List<MovementJpaEntity> movements) {
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

    public ClientJpaEntity getClient() {
        return client;
    }

    public void setClient(ClientJpaEntity client) {
        this.client = client;
    }

    public List<CreditCardJpaEntity> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(List<CreditCardJpaEntity> creditCards) {
        this.creditCards = creditCards;
    }

    public List<MovementJpaEntity> getMovements() {
        return movements == null ? List.of() : movements;
    }

    public void setMovements(List<MovementJpaEntity> movements) {
        this.movements = movements;
    }

    @Override
    public String toString() {
        return "BankAccountJpaEntity{" +
                "id=" + id +
                ", iban='" + iban + '\'' +
                ", balance=" + balance +
                ", client=" + client +
                ", creditCards=" + creditCards +
                ", movements=" + movements +
                '}';
    }
}
