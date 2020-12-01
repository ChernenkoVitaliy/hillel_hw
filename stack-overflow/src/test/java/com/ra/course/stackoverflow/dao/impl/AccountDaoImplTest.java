package com.ra.course.stackoverflow.dao.impl;

import com.ra.course.stackoverflow.dao.AccountDao;
import com.ra.course.stackoverflow.entity.Account;
import com.ra.course.stackoverflow.entity.enums.AccountStatus;
import com.ra.course.stackoverflow.entity.jooq.tables.records.AccountRecord;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockConnection;
import org.jooq.tools.jdbc.MockDataProvider;
import org.jooq.tools.jdbc.MockExecuteContext;
import org.jooq.tools.jdbc.MockResult;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;

import static com.ra.course.stackoverflow.entity.jooq.tables.AccountTable.ACCOUNT_TABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class AccountDaoImplTest {
    private final long ID = 1L;
    private final Account account = createAccount(ID);


    // Initialise data provider
    private final MockDataProvider provider = new AccountDaoImplTest.MockProvider();
    private final MockConnection connection = new MockConnection(provider);

    // Pass the mock connection to a jOOQ DSLContext:
    private final DSLContext dslContext = DSL.using(connection, SQLDialect.H2);

    // Initialise AccountRepositoryImpl with mocked DSL
    private final AccountDao accountDao = new AccountDaoImpl(dslContext);


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
        //given
        final var before = createAccount(777L);

        //when
        before.setName("new Name");
        final var result = accountDao.update(before);

        //then
        assertEquals(before.getName(), result.getName());
    }

    @Test
    public void whenDeleteAccount_andAccountExists_ThenReturnTrue() {
        //given
        final var accountForDeleting = createAccount(888L);

        //when
        final var result = accountDao.delete(accountForDeleting);

        //then
        assertTrue(result);
    }

    @Test
    public void whenDeleteAccount_andAccountExists_ThenSetAccountStatusARCHIVED() {
        //given
        final var accountForDeleting = createAccount(888L);

        //when
        accountDao.delete(accountForDeleting);
        final var result =  accountDao.getById(accountForDeleting.getId()).get();

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
                "John Test",
                "john_test@some.com",
                "(123) 321 45 67");
    }

    class MockProvider implements MockDataProvider {


        @Override
        public MockResult[] execute(MockExecuteContext ctx){

            //DSLContext need to create org.jooq.Result and org.jooq.Record objects
            DSLContext create = DSL.using(SQLDialect.H2);
            MockResult[] mock = new MockResult[1];

            // The execute context contains SQL string(s), bind values, and other meta-data
            String sql = ctx.sql().toUpperCase();
            var value = ctx.bindings();

            //Results for mock
            final var resultAccount = create.newResult(ACCOUNT_TABLE.ID, ACCOUNT_TABLE.PASSWORD,
                    ACCOUNT_TABLE.ACCOUNT_STATUS, ACCOUNT_TABLE.NAME, ACCOUNT_TABLE.EMAIL, ACCOUNT_TABLE.PHONE, ACCOUNT_TABLE.REPUTATION);

            //Objects for mocked result
            final var accountRecordID1 = new AccountRecord(1, "password_1", AccountStatus.ACTIVE.toString().toUpperCase(Locale.US),
                    "Test Name1", "test_name1@some.com", "(123) 321 45 67", 0);
            final var accountRecordID777 = new AccountRecord(777, "password_1", AccountStatus.ACTIVE.toString().toUpperCase(Locale.US),
                    "new Name", "test_name1@some.com", "(123) 321 45 67", 0);
            final var accountRecordID888 = new AccountRecord(888, "password_1", AccountStatus.ARCHIVED.toString().toUpperCase(Locale.US),
                    "new Name", "test_name1@some.com", "(123) 321 45 67", 0);

            //Stipulations for returning different results
            if (sql.startsWith("INSERT") || (sql.startsWith("SELECT \"STACKOVERFLOW\".\"ACCOUNT\""))&& value[0].equals(1)) {
                resultAccount.add(accountRecordID1);

                mock[0] = new MockResult(1, resultAccount);
            }else if (sql.startsWith("UPDATE \"STACKOVERFLOW\".\"ACCOUNT\"") || (sql.startsWith("SELECT \"STACKOVERFLOW\".\"ACCOUNT\"") && value[0].equals(777))) {
                resultAccount.add(accountRecordID777);

                mock[0] = new MockResult(1, resultAccount);
            }else if (sql.startsWith("SELECT \"STACKOVERFLOW\".\"ACCOUNT\"") && value[0].equals(888)) {
                resultAccount.add(accountRecordID888);

                mock[0] = new MockResult(1, resultAccount);
            }else if (sql.startsWith("SELECT \"STACKOVERFLOW\".\"ACCOUNT\"") && (value[0].equals("Name") || value[0].equals("test_name1@some.com"))) {
                resultAccount.add(accountRecordID1);

                mock[0] = new MockResult(1, resultAccount);
            }else {

                mock[0] = new MockResult(1, resultAccount);
            }

            return mock;
        }
    }
}
