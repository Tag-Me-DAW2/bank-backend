package com.tagme.tagme_bank_back.domain.model;

import java.util.List;
import java.util.Objects;

public class Client {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String lastName1;
    private String lastName2;
    private String dni;
    private String api_key;

    public Client(Long id, String username, String password, String name, String lastName1, String lastName2, String dni, String api_key) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastName1 = lastName1;
        this.lastName2 = lastName2;
        this.dni = dni;
        this.api_key = api_key;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName1() {
        return lastName1;
    }

    public void setLastName1(String lastName1) {
        this.lastName1 = lastName1;
    }

    public String getLastName2() {
        return lastName2;
    }

    public void setLastName2(String lastName2) {
        this.lastName2 = lastName2;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getapi_key() {
        return api_key;
    }

    public void setapi_key(String api_key) {
        this.api_key = api_key;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;
        return Objects.equals(id, client.id) && Objects.equals(username, client.username) && Objects.equals(password, client.password) && Objects.equals(name, client.name) && Objects.equals(lastName1, client.lastName1) && Objects.equals(lastName2, client.lastName2) && Objects.equals(dni, client.dni) && Objects.equals(api_key, client.api_key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, name, lastName1, lastName2, dni, api_key);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", lastName1='" + lastName1 + '\'' +
                ", lastName2='" + lastName2 + '\'' +
                ", dni='" + dni + '\'' +
                ", api_key='" + api_key + '\'' +
                '}';
    }
}
