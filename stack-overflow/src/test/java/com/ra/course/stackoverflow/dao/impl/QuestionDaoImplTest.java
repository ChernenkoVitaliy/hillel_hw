package com.ra.course.stackoverflow.dao.impl;

import com.ra.course.stackoverflow.dao.AccountDao;
import com.ra.course.stackoverflow.dao.QuestionDao;
import com.ra.course.stackoverflow.entity.Account;
import com.ra.course.stackoverflow.entity.Answer;
import com.ra.course.stackoverflow.entity.Member;
import com.ra.course.stackoverflow.entity.Question;
import com.ra.course.stackoverflow.entity.enums.AccountStatus;
import com.ra.course.stackoverflow.entity.enums.QuestionClosingRemark;
import com.ra.course.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.stackoverflow.entity.jooq.tables.records.AccountRecord;
import com.ra.course.stackoverflow.entity.jooq.tables.records.AnswerRecord;
import com.ra.course.stackoverflow.entity.jooq.tables.records.QuestionRecord;
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
import static com.ra.course.stackoverflow.entity.jooq.tables.QuestionTable.QUESTION_TABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class QuestionDaoImplTest {
    private final long ID = 1L;
    private final Question question = createQuestion(ID);
    private final Member author = new Member(createAccount(ID));
    private final Answer answer = createAnswer(ID);

    // Initialise data provider
    private final MockDataProvider provider = new QuestionDaoImplTest.MockProvider();
    private final MockConnection connection = new MockConnection(provider);

    // Pass the mock connection to a jOOQ DSLContext:
    private final DSLContext dslContext = DSL.using(connection, SQLDialect.H2);

    // Initialise AccountRepositoryImpl with mocked DSL
    private final AccountDao accountDao = new AccountDaoImpl(dslContext);
    private final QuestionDao questionDao = new QuestionDaoImpl(dslContext, accountDao);

    @Test
    public void whenFindQuestionByIdAndQuestionPresentInDataBaseThenReturnQuestion() {
        //when
        var question = questionDao.getById(1L).get();

        //then
        assertEquals(question.getId(), 1L);
    }

    @Test
    public void whenFindQuestionByIdAndQuestionNotExists_ThenReturnOptionalEmpty() {
        //when
        final var result = questionDao.getById(666L);

        //then
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    public void whenSaveQuestion_ThenReturnQuestionWithID() {
        //when
        final var result = questionDao.save(question);

        //then
        assertThat(result.getId() > 0);
    }

    @Test
    public void whenUpdateQuestion_ThenReturnUpdatedQuestion() {
        //given
        final var before = createQuestion(777L);

        //when
        before.setTitle("New title.");
        final var result = questionDao.update(before);

        //then
        assertEquals(before.getTitle(), result.getTitle());
    }

    @Test
    public void whenDeleteQuestion_andQuestionExists_ThenReturnTrue() {
        //given
        final var questionForDeleting = createQuestion(888L);

        //when
        final var result = questionDao.delete(questionForDeleting);

        //then
        assertTrue(result);
    }

    @Test
    public void whenDeleteQuestion_andQuestionExists_ThenSetQuestionStatusDELETED() {
        //given
        final var questionForDeleting = createQuestion(888L);

        //when
        questionDao.delete(questionForDeleting);
        final var result =  questionDao.getById(questionForDeleting.getId()).get();

        //then
        assertThat(result.getStatus().equals(QuestionStatus.DELETED));
    }

    @Test
    public void whenDeleteQuestion_andQuestionNotExists_ThenReturnFalse() {
        //given
        final var questionForDeleting = createQuestion(666L);

        //when
        final var result = questionDao.delete(questionForDeleting);

        //then
        assertFalse(result);
    }

    @Test
    public void whenFindQuestionByAuthorThenReturnList() {
        //when
        List<Question> listResult = questionDao.getByAuthor(author);

        //then
        assertThat(listResult.size() > 0).isTrue();
    }

    @Test
    public void whenFindQuestionByTitleThenReturnList() {
        //when
        List<Question> listResult = questionDao.getByTitle("title");

        //then
        assertThat(listResult.size() > 0).isTrue();
    }

    @Test
    public void whenFindQuestionByAnswerThenReturnList() {
        //when
        final var result = questionDao.getByAnswer(answer);

        //then
        assertThat(result.getId() > 0).isTrue();
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
            final var resultQuestion = create.newResult(QUESTION_TABLE.ID, QUESTION_TABLE.TITLE,
                    QUESTION_TABLE.DESCRIPTION, QUESTION_TABLE.VIEW_COUNT, QUESTION_TABLE.VOTE_COUNT, QUESTION_TABLE.CREATED,
                    QUESTION_TABLE.UPDATED, QUESTION_TABLE.STATUS, QUESTION_TABLE.CLOSING_REMARK, QUESTION_TABLE.AUTHOR_ID);
            final var resultAnswer = create.newResult(ANSWER_TABLE.ID, ANSWER_TABLE.ANSWER_TEXT,
                    ANSWER_TABLE.ACCEPTED, ANSWER_TABLE.VOTE_COUNT, ANSWER_TABLE.FLAG_COUNT, ANSWER_TABLE.CREATED,
                    ANSWER_TABLE.AUTHOR_ID, ANSWER_TABLE.QUESTION_ID);

            //Objects for mocked result
            final var accountRecordID1 = new AccountRecord(1, "password_1", AccountStatus.ACTIVE.toString().toUpperCase(Locale.US),
                    "Test Name1", "test_name1@some.com", "(123) 321 45 67", 0);
            final var questionRecordID1 = new QuestionRecord(1, "Some question title.", "Some question description", 0, 0,
                    Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()),
                    QuestionStatus.OPEN.toString().toUpperCase(Locale.US), QuestionClosingRemark.DUPLICATE.toString().toUpperCase(Locale.US), 1);
            final var questionRecordID777 = new QuestionRecord(777, "New title.", "Some question description", 0, 0,
                    Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()),
                    QuestionStatus.OPEN.toString().toUpperCase(Locale.US), QuestionClosingRemark.DUPLICATE.toString().toUpperCase(Locale.US), 1);
            final var questionRecordID888 = new QuestionRecord(888, "Some question title.", "Some question description", 0, 0,
                    Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()),
                    QuestionStatus.DELETED.toString().toUpperCase(Locale.US), QuestionClosingRemark.DUPLICATE.toString().toUpperCase(Locale.US), 1);
            final var answerRecordID1 = new AnswerRecord(1, "Some answer text", (byte)0, 0, 0, Timestamp.valueOf(LocalDateTime.now()),
                    1, 1);


            //Stipulations for returning different results
            if (sql.startsWith("INSERT INTO \"STACKOVERFLOW\".\"QUESTION\"") || sql.startsWith("SELECT \"STACKOVERFLOW\".\"QUESTION\"") && value[0].equals(1)) {
                resultQuestion.add(questionRecordID1);

                mock[0] = new MockResult(1, resultQuestion);
            }else if (sql.startsWith("SELECT \"STACKOVERFLOW\".\"ACCOUNT\"") && value[0].equals(1)) {
                resultAccount.add(accountRecordID1);

                mock[0] = new MockResult(1, resultAccount);
            }else if (sql.startsWith("UPDATE \"STACKOVERFLOW\".\"QUESTION\"") || (sql.startsWith("SELECT \"STACKOVERFLOW\".\"QUESTION\"") && value[0].equals(777))) {
                resultQuestion.add(questionRecordID777);

                mock[0] = new MockResult(1, resultQuestion);
            }else if (sql.startsWith("SELECT \"STACKOVERFLOW\".\"QUESTION\"") && value[0].equals(888)) {
                resultQuestion.add(questionRecordID888);

                mock[0] = new MockResult(1, resultQuestion);
            }else if (sql.startsWith("SELECT \"STACKOVERFLOW\".\"QUESTION\"") && value[0].equals("title")) {
                resultQuestion.add(questionRecordID777);

                mock[0] = new MockResult(1, resultQuestion);
            } else if (sql.startsWith("SELECT \"STACKOVERFLOW\".\"ANSWER\"") && value[0].equals(1)) {
                resultAnswer.add(answerRecordID1);

                mock[0] = new MockResult(1, resultAnswer);
            }else {

                mock[0] = new MockResult(1, resultQuestion);
            }

            return mock;
        }
    }
}
