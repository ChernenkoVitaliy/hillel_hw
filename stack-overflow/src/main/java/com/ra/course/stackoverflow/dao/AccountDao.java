package com.ra.course.stackoverflow.dao;

import com.ra.course.stackoverflow.entity.Account;

import java.util.List;

public interface AccountDao extends GeneralDao<Account> {

    List<Account> getByName(String name);

    List<Account> getByEmail(String email);
}
