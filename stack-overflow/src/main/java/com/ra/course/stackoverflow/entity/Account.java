package com.ra.course.stackoverflow.entity;

import com.ra.course.stackoverflow.entity.enums.AccountStatus;

import java.util.Objects;

public class Account {

    private final long id;
    private String password;
    private AccountStatus status;
    private String name;
    private String email;
    private String phone;
    private int reputation;

    public Account(long id, String password, AccountStatus status, String name, String email, String phone) {
        this.id = id;
        this.password = password;
        this.status = status;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id &&
                reputation == account.reputation &&
                password.equals(account.password) &&
                status == account.status &&
                name.equals(account.name) &&
                email.equals(account.email) &&
                phone.equals(account.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, status, name, email, phone, reputation);
    }
}
