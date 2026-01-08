package com.tagme.tagme_bank_back.persistence.dao.jpa.entity;


import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_sessions")
public class SessionJpaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private ClientJpaEntity client;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public SessionJpaEntity() {}

    public SessionJpaEntity(ClientJpaEntity client, String token, LocalDateTime createdAt) {
        this.client = client;
        this.token = token;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientJpaEntity getUser() {
        return client;
    }

    public void setUser(ClientJpaEntity user) {
        this.client = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
