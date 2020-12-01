package com.ra.course.stackoverflow.dao.impl;

import com.ra.course.stackoverflow.dao.AccountDao;
import com.ra.course.stackoverflow.dao.AnswerDao;
import com.ra.course.stackoverflow.entity.Account;
import com.ra.course.stackoverflow.entity.Answer;
import com.ra.course.stackoverflow.entity.Member;
import com.ra.course.stackoverflow.entity.Question;
import com.ra.course.stackoverflow.entity.enums.AccountStatus;
import com.ra.course.stackoverflow.entity.jooq.tables.records.AccountRecord;
import com.ra.course.stackoverflow.entity.jooq.tables.records.AnswerRecord;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockConnection;
import org.jooq.tools.jdbc.MockDataProvider;
import org.jooq.tools.jdbc.MockExecuteContext;
import org.jooq.tools.jdbc.MockResult;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import static com.ra.course.stackoverflow.entity.jooq.tables.AccountTable.ACCOUNT_TABLE;
import static com.ra.course.stackoverflow.entity.jooq.tables.AnswerTable.ANSWER_TABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class AnswerDaoImplTest {
    private final long ID = 1L;
    private final Member author = new Member(createAccount(ID));
    private final Question question = createQuestion(ID);
    private final Answer answer = createAnswer(ID);

    // Initialise data provider
    private final MockDataProvider provider = new AnswerDaoImplTest.MockProvider();
    private final MockConnection connection = new MockConnection(provider);

    // Pass the mock connection to a jOOQ DSLContext:
    private final DSLContext dslContext = DSL.using(connection, SQLDialect.H2);

    // Initialise AccountRepositoryImpl with mocked DSL
    private final AccountDao accountDao = new AccountDaoImpl(dslContext);
    private final AnswerDao answerDao = new AnswerDaoImpl(dslContext, accountDao);


    @Test
    public void whenFindAnswerByIdAndAnswerPresentInDataBaseThenReturnAnswer() {
        //when
        var answerResult = answerDao.getById(1L).get();

        //then
        assertEquals(answerResult.getId(), 1L);
    }

    @Test
    public void whenFindAnswerByIdAndAnswerNotExists_ThenReturnOptionalEmpty() {
        //when
        final var result = answerDao.getById(666L);

        //then
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    public void whenSaveAnswer_ThenReturnAnswerWithID() {
        //when
        final var result = answerDao.save(answer);

        //then
        assertThat(result.getId() > 0);
    }

    @Test
    public void whenUpdateAnswer_ThenReturnUpdatedAnswer() {
        //given
        final var before = createAnswer(777L);

        //when
        before.setAnswerText("New answer text");
        final var result = answerDao.update(before);

        //then
        assertEquals(before.getAnswerText(), result.getAnswerText());
    }

    @Test
    public void whenDeleteAnswer_andAnswerExists_ThenReturnTrue() {
        //given
        final var answerForDeleting = createAnswer(888L);

        //when
        final var result = answerDao.delete(answerForDeleting);

        //then
        assertTrue(result);
    }

    @Test
    public void whenDeleteAnswer_andAnswerNotExists_ThenReturnFalse() {
        //given
        final var answerForDeleting = createAnswer(666L);

        //when
        final var result = answerDao.delete(answerForDeleting);

        //then
        assertFalse(result);
    }


    @Test
    public void whenFindAnswerByAuthorThenReturnList() {
        //when
        List<Answer> listResult = answerDao.getByAuthor(author);

        //then
        assertThat(listResult.size() > 0).isTrue();
    }

    @Test
    public void whenFindAnswerByQuestionThenReturnList() {
        //when
        List<Answer> listResult = answerDao.getByQuestion(question);

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

    private Question createQuestion(final  long id) {
        final Member author = new Member(createAccount(id));
        return new Question(id,
                "Some question title",
                "Some question description",
                author);
    }

    private Answer createAnswer(final long id) {
        return new Answer(id,
                "Some answer text.",
                author,
                question.getId());
    }

     class MockProvider implements MockDataProvider {


        @Override
        public MockResult[] execute(MockExecuteContext ctx) {

            //DSLContext need to create org.jooq.Result and org.jooq.Record objects
            DSLContext create = DSL.using(SQLDialect.H2);
            MockResult[] mock = new MockResult[1];

            // The execute context contains SQL string(s), bind values, and other meta-data
            String sql = ctx.sql().toUpperCase();
            var value = ctx.bindings();

            //Results for mock
            final var resultAccount = create.newResult(ACCOUNT_TABLE.ID, ACCOUNT_TABLE.PASSWORD,
                    ACCOUNT_TABLE.ACCOUNT_STATUS, ACCOUNT_TABLE.NAME, ACCOUNT_TABLE.EMAIL, ACCOUNT_TABLE.PHONE, ACCOUNT_TABLE.REPUTATION);
            final var resultAnswer = create.newResult(ANSWER_TABLE.ID, ANSWER_TABLE.ANSWER_TEXT,
                    ANSWER_TABLE.ACCEPTED, ANSWER_TABLE.VOTE_COUNT, ANSWER_TABLE.FLAG_COUNT, ANSWER_TABLE.CREATED,
                    ANSWER_TABLE.AUTHOR_ID, ANSWER_TABLE.QUESTION_ID);

            //Objects for mocked result
            final var accountRecordID1 = new AccountRecord(1, "password_1", AccountStatus.ACTIVE.toString().toUpperCase(Locale.US),
                    "Test Name1", "test_name1@some.com", "(123) 321 45 67", 0);
            final var answerRecordID1 = new AnswerRecord(1, "Some answer text", (byte)0, 0, 0, Timestamp.valueOf(LocalDateTime.now()),
                    1, 1);
            final var answerRecordID777 = new AnswerRecord(777, "New answer text", (byte)0, 0, 0, Timestamp.valueOf(LocalDateTime.now()),
                    1, 1);
            final var answerRecordID888 = new AnswerRecord(888, "Some answer text", (byte)0, 0, 0, Timestamp.valueOf(LocalDateTime.now()),
                    1, 1);


            //Stipulations for returning different results
            if (sql.startsWith("INSERT INTO \"STACKOVERFLOW\".\"ANSWER\"") || sql.startsWith("SELECT \"STACKOVERFLOW\".\"ANSWER\"") && value[0].equals(1)) {
                resultAnswer.add(answerRecordID1);

                mock[0] = new MockResult(1, resultAnswer);
            }else if (sql.startsWith("SELECT \"STACKOVERFLOW\".\"ACCOUNT\"") && value[0].equals(1)) {
                resultAccount.add(accountRecordID1);

                mock[0] = new MockResult(1, resultAccount);
            }else if (sql.startsWith("UPDATE \"STACKOVERFLOW\".\"ANSWER\"") || (sql.startsWith("SELECT \"STACKOVERFLOW\".\"ANSWER\"") && value[0].equals(777))) {
                resultAnswer.add(answerRecordID777);

                mock[0] = new MockResult(1, resultAnswer);
            }else if (sql.startsWith("SELECT \"STACKOVERFLOW\".\"ANSWER\"") && value[0].equals(888)) {
                resultAnswer.add(answerRecordID888);

                mock[0] = new MockResult(1, resultAnswer);
            }else if (sql.startsWith("DELETE") && value[0].equals(888)) {
                resultAnswer.add(null);

                mock[0] = new MockResult(1, resultAnswer);
            }else {

                mock[0] = new MockResult(1, resultAnswer);
            }

            return mock;
        }
    }
}
