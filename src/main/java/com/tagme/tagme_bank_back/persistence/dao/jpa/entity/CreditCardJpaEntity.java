package com.tagme.tagme_bank_back.persistence.dao.jpa.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "tb_credit_cards")
public class CreditCardJpaEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "bank_account_id", nullable = false)
//    private BankAccountJpaEntity bankAccount;
    @Column(name = "number", nullable = false, unique = true)
    private String number;
    @Column(name = "expiration_date", nullable = false)
    private String expirationDate;
    @Column(name = "cvv", nullable = false)
    private String cvv;
    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column(name = "created_at", nullable = false, updatable = false)
    private String createdAt;


    public CreditCardJpaEntity() {
    }

    public CreditCardJpaEntity(Long id, String number, String expirationDate, String cvv, String fullName) {
        this.id = id;
        this.number = number;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
