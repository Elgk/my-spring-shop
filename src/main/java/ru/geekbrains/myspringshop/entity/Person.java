package ru.geekbrains.myspringshop.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="person")
public class Person {
    @Id
    private UUID id;

    @Column(name = "login", length = 128, unique = true)
    private String login;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address", length = 1024)
    private String address;

    @Column(name = "balance")
    private String balance;

    @Column(name = "role", length = 16)
    private String role;

    @Column(name = "patronymic", length = 128)
    private String  patronymic;

    @Column(name = "email", length = 256)
    private String email;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "password", length = 128)
    private String password;

    @Column
    private UUID keycloakId;

    @Column(name = "is_disabled", nullable = false)
    private boolean isDisabled;

    @PrePersist
    public void init() {
        if(this.id == null) {
            this.id = UUID.randomUUID();
        }

        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public Person setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Person setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Person setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Person setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Person setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getAddress() {
        return address;
    }

    public String getBalance() {
        return balance;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public Person setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Person setAddress(String address) {
        this.address = address;
        return this;
    }

    public Person setBalance(String balance) {
        this.balance = balance;
        return this;
    }

    public Person setRole(String role) {
        this.role = role;
        return this;
    }

    public Person setEmail(String email) {
        this.email = email;
        return  this;
    }

    public String getLogin() {
        return login;
    }

    public Person setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public Person setPatronymic(String patronymic) {
        this.patronymic = patronymic;
        return this;
    }

    public UUID getKeycloakId() {
        return keycloakId;
    }

    public Person setKeycloakId(UUID keycloakId) {
        this.keycloakId = keycloakId;
        return this;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public Person setDisabled(boolean disabled) {
        this.isDisabled = disabled;
        return  this;
    }
}
