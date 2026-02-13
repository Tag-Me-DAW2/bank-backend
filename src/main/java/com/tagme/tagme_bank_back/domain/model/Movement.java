package com.tagme.tagme_bank_back.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Movement {
    private Long id;
    private MovementType type;
    private MovementOrigin origin;
    private CreditCard oCreditCard;
    private LocalDate date;
    private BigDecimal amount;
    private String concept;

    public Movement(Long id, MovementType type, MovementOrigin origin, CreditCard oCreditCard, LocalDate date, BigDecimal amount, String concept) {
        this.id = id;
        this.type = type;
        this.origin = origin;
        this.oCreditCard = oCreditCard;
        this.date = date;
        setAmount(amount);
        this.concept = concept;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public CreditCard getoCreditCard() {
        return oCreditCard;
    }

    public void setoCreditCard(CreditCard oCreditCard) {
        this.oCreditCard = oCreditCard;
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
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que 0");
        }
        this.amount = amount;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    @Override
    public String toString() {
        return "Movement{" +
                "id=" + id +
                ", type=" + type +
                ", origin=" + origin +
                ", oCreditCard=" + oCreditCard +
                ", date=" + date +
                ", amount=" + amount +
                ", concept='" + concept + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movement movement = (Movement) o;
        return Objects.equals(id, movement.id) && type == movement.type && origin == movement.origin && Objects.equals(oCreditCard, movement.oCreditCard) && Objects.equals(date, movement.date) && Objects.equals(amount, movement.amount) && Objects.equals(concept, movement.concept);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, origin, oCreditCard, date, amount, concept);
    }
}
