package com.ra.course.stackoverflow.dao.impl;

import com.ra.course.stackoverflow.dao.AccountDao;
import com.ra.course.stackoverflow.entity.Account;
import com.ra.course.stackoverflow.entity.enums.AccountStatus;
import com.ra.course.stackoverflow.entity.jooq.tables.records.AccountRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ra.course.stackoverflow.entity.jooq.tables.AccountTable.ACCOUNT_TABLE;

@Repository
public class AccountDaoImpl implements AccountDao {


    private final transient DSLContext dslContext;

    @Autowired
    public AccountDaoImpl(final DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public Optional<Account> getById(final long id) {

        final var recordQuestion = dslContext.fetchOne(ACCOUNT_TABLE, ACCOUNT_TABLE.ID.eq((int) id));

        if (recordQuestion == null) {
            return Optional.empty();
        }

        return Optional.of(mapperAccount(recordQuestion));
    }

    @Override
    public Account save(final Account account) {

        final var accountResult = dslContext.insertInto(ACCOUNT_TABLE, ACCOUNT_TABLE.PASSWORD, ACCOUNT_TABLE.ACCOUNT_STATUS, ACCOUNT_TABLE.NAME,
                ACCOUNT_TABLE.EMAIL, ACCOUNT_TABLE.PHONE, ACCOUNT_TABLE.REPUTATION)
                .values(account.getPassword(), account.getStatus().toString().toUpperCase(Locale.US), account.getName(),
                        account.getEmail(), account.getPhone(), account.getReputation())
                .returning()
                .fetchOne();
        return mapperAccount(accountResult);
    }

    @Override
    public Account update(final Account account) {

        final var accountRecord = dslContext.update(ACCOUNT_TABLE)
                .set(ACCOUNT_TABLE.PASSWORD, account.getPassword())
                .set(ACCOUNT_TABLE.ACCOUNT_STATUS, account.getStatus().toString().toUpperCase(Locale.US))
                .set(ACCOUNT_TABLE.NAME, account.getName())
                .set(ACCOUNT_TABLE.EMAIL, account.getEmail())
                .set(ACCOUNT_TABLE.PHONE, account.getPhone())
                .set(ACCOUNT_TABLE.REPUTATION, account.getReputation())
                .where(ACCOUNT_TABLE.ID.eq(Math.toIntExact(account.getId())))
                .returning()
                .fetchOne();

        return mapperAccount(accountRecord);
    }

    @Override
    public boolean delete(final Account account) {

        final var accountFromDb = getById(account.getId());

        if (accountFromDb.isPresent()) {
            accountFromDb.get().setStatus(AccountStatus.ARCHIVED);
            update(accountFromDb.get());
            return true;
        }
        return false;
    }

    @Override
    public List<Account> getByName(final String name) {

            return dslContext.selectFrom(ACCOUNT_TABLE)
                    .where(ACCOUNT_TABLE.NAME.contains(name))
                    .fetchStream()
                    .map(this::mapperAccount)
                    .collect(Collectors.toList());
    }

    @Override
    public List<Account> getByEmail(final String email) {

        return dslContext.selectFrom(ACCOUNT_TABLE)
                .where(ACCOUNT_TABLE.EMAIL.contains(email))
                .fetchStream()
                .map(this::mapperAccount)
                .collect(Collectors.toList());
    }

    private Account mapperAccount(final AccountRecord record) {

        return new Account((long)record.getId(),
                record.getPassword(),
                AccountStatus.valueOf(record.getAccountStatus().toUpperCase(Locale.US)),
                record.getName(),
                record.getEmail(),
                record.getPhone());
    }
}
