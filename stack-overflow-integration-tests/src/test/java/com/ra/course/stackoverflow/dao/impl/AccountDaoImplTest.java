package com.ra.course.stackoverflow.dao.impl;

import com.ra.course.stackoverflow.dao.AccountDao;
import com.ra.course.stackoverflow.entity.Account;
import com.ra.course.stackoverflow.entity.enums.AccountStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
public class AccountDaoImplTest {
    private final long ID = 1L;
    private final Account account = createAccount(ID);
    @Autowired
    private AccountDao accountDao;


    @Test
    public void whenFindAccountByIdAndAccountPresentInDataBaseThenReturnAccount() {
        //when
        var account = accountDao.getById(1L).get();

        //then
        assertEquals(account.getId(), 1L);
    }

    @Test
    public void whenFindAccountByIdAndAccountNotExists_ThenReturnOptionalEmpty() {
        //when
        final var result = accountDao.getById(666L);

        //then
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    public void whenSaveAccount_ThenReturnAccountWithID() {
        //when
        final var result = accountDao.save(account);

        //then
        assertThat(result.getId() > 0);
    }

    @Test
    public void whenUpdateAccount_ThenReturnUpdatedAccount() {
        //when
        account.setName("new Name");
        final var result = accountDao.update(account);

        //then
        assertEquals(account.getName(), result.getName());
    }

    @Test
    @Rollback
    public void whenDeleteAccount_andAccountExists_ThenReturnTrue() {
        //when
        final var result = accountDao.delete(account);

        //then
        assertTrue(result);
    }

    @Test
    public void whenDeleteAccount_andAccountExists_ThenSetAccountStatusARCHIVED() {
        //when
        accountDao.delete(account);
        final var result =  accountDao.getById(account.getId()).get();

        //then
        assertThat(result.getStatus().equals(AccountStatus.ARCHIVED));
    }

    @Test
    public void whenDeleteAccount_andAccountNotExists_ThenReturnFalse() {
        //given
        final var accountForDeleting = createAccount(666L);

        //when
        final var result = accountDao.delete(accountForDeleting);

        //then
        assertFalse(result);
    }

    @Test
    public void whenFindAccountByNameThenReturnList() {
        //when
        List<Account> listResult = accountDao.getByName("Name");

        //then
        assertThat(listResult.size() > 0).isTrue();
    }

    @Test
    public void whenFindAccountByEmailThenReturnList() {
        //when
        List<Account> listResult = accountDao.getByEmail("test_name1@some.com");

        //then
        assertThat(listResult.size() > 0).isTrue();
    }


    private Account createAccount(final long id) {
        return new Account(id,
                "password",
                AccountStatus.ACTIVE,
                "John Smith",
                "john_smith@some.com",
                "(123) 456 87 99");
    }
}
