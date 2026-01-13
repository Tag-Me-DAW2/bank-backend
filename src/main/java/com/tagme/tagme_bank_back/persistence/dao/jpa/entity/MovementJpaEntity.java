package com.tagme.tagme_bank_back.persistence.dao.jpa.entity;

import com.tagme.tagme_bank_back.domain.model.CreditCard;
import com.tagme.tagme_bank_back.domain.model.MovementOrigin;
import com.tagme.tagme_bank_back.domain.model.MovementType;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_movements")
public class MovementJpaEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private BankAccountJpaEntity bankAccount;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MovementType type;

    @Column(name = "origin", nullable = false)
    @Enumerated(EnumType.STRING)
    private MovementOrigin origin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_credit_card_id")
    private CreditCardJpaEntity originCreditCard;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "concept", nullable = false)
    private String concept;

    public MovementJpaEntity() {
    }

    public MovementJpaEntity(MovementType type, MovementOrigin origin, CreditCardJpaEntity originCreditCard, LocalDate date, BigDecimal amount, String concept) {
        this.type = type;
        this.origin = origin;
        this.originCreditCard = originCreditCard;
        this.date = date;
        this.amount = amount;
        this.concept = concept;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BankAccountJpaEntity getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccountJpaEntity bankAccount) {
        this.bankAccount = bankAccount;
    }

    public MovementType getType() {
        return type;
    }

    public void setType(MovementType type) {
        this.type = type;
    }

    public MovementOrigin getOrigin() {
        return origin;
    }

    public void setOrigin(MovementOrigin origin) {
        this.origin = origin;
    }

    public CreditCardJpaEntity getOriginCreditCard() {
        return originCreditCard;
    }

    public void setOriginCreditCard(CreditCardJpaEntity originCreditCard) {
        this.originCreditCard = originCreditCard;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }


}
