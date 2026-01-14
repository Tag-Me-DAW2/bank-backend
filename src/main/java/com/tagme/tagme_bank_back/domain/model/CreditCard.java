package com.tagme.tagme_bank_back.domain.model;

import java.util.Objects;

public class CreditCard {
    private Long id;
    private String number;
    private String expirationDate;
    private String cvv;
    private String fullName;

    public CreditCard(Long id, String number, String expirationDate, String cvv, String fullName) {
        this.id = id;
        this.number = number;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        setFullName(fullName);
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
        this.fullName = fullName.toUpperCase();
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", cvv='" + cvv + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return Objects.equals(id, that.id) && Objects.equals(number, that.number) && Objects.equals(expirationDate, that.expirationDate) && Objects.equals(cvv, that.cvv) && Objects.equals(fullName, that.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, expirationDate, cvv, fullName);
    }
}
