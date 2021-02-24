package com.ra.course.stackoverflow.dto;

import com.ra.course.stackoverflow.entity.enums.AccountStatus;

import java.util.Objects;

public class AccountDto {

    private final Long id;
    private String password;
    private AccountStatus status;
    private String name;
    private String email;
    private String phone;
    private int reputation;

    public AccountDto(Long id, String password, AccountStatus status, String name, String email, String phone) {
        this.id = id;
        this.password = password;
        this.status = status;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public Long getId() {
        return id;
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
}
