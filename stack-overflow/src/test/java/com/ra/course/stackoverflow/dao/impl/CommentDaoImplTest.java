package com.ra.course.stackoverflow.dao.impl;

import com.ra.course.stackoverflow.dao.AccountDao;
import com.ra.course.stackoverflow.dao.CommentDao;
import com.ra.course.stackoverflow.entity.*;
import com.ra.course.stackoverflow.entity.enums.AccountStatus;
import com.ra.course.stackoverflow.entity.jooq.tables.records.AccountRecord;
import com.ra.course.stackoverflow.entity.jooq.tables.records.CommentRecord;
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
import static com.ra.course.stackoverflow.entity.jooq.tables.CommentTable.COMMENT_TABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class CommentDaoImplTest {
    private final long ID = 1L;
    private final Member author = new Member(createAccount(ID));
    private final Question question = createQuestion(ID);
    private final Answer answer = createAnswer(ID);
    private final Comment comment = createComment(ID);

    // Initialise data provider
    private final MockDataProvider provider = new CommentDaoImplTest.MockProvider();
    private final MockConnection connection = new MockConnection(provider);

    // Pass the mock connection to a jOOQ DSLContext:
    private final DSLContext dslContext = DSL.using(connection, SQLDialect.H2);

    // Initialise AccountRepositoryImpl with mocked DSL
    private final AccountDao accountDao = new AccountDaoImpl(dslContext);
    private final CommentDao commentDao = new CommentDaoImpl(dslContext, accountDao);

    @Test
    public void whenFindCommentByIdAndCommentPresentInDataBaseThenReturnComment() {
        //when
        var commentResult = commentDao.getById(1L).get();

        //then
        assertEquals(commentResult.getId(), 1L);
    }

    @Test
    public void whenFindCommentByIdAndCommentNotExists_ThenReturnOptionalEmpty() {
        //when
        final var result = commentDao.getById(666L);

        //then
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    public void whenSaveComment_ThenReturnCommentWithID() {
        //when
        final var result = commentDao.save(comment);

        //then
        assertThat(result.getId() > 0);
    }

    @Test
    public void whenUpdateComment_ThenReturnUpdatedComment() {
        //given
        final var before = createComment(777L);

        //when
        before.setText("New text");
        final var result = commentDao.update(before);

        //then
        assertEquals(before.getText(), result.getText());
    }

    @Test
    public void whenDeleteComment_andCommentExists_ThenReturnTrue() {
        //given
        final var commentForDeleting = createComment(888L);

        //when
        final var result = commentDao.delete(commentForDeleting);

        //then
        assertTrue(result);
    }

    @Test
    public void whenDeleteComment_andCommentNotExists_ThenReturnFalse() {
        //given
        final var commentForDeleting = createComment(666L);

        //when
        final var result = commentDao.delete(commentForDeleting);

        //then
        assertFalse(result);
    }

    @Test
    public void whenFindCommentByAnswerThenReturnList() {
        //when
        List<Comment> listResult = commentDao.getByAnswer(answer);

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

    private Comment createComment(final long id) {
        return new Comment(id,
                "Some comment text",
                answer.getId(),
                author);
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
            final var resultComment = create.newResult(COMMENT_TABLE.ID, COMMENT_TABLE.TEXT,
                    COMMENT_TABLE.CREATED, COMMENT_TABLE.FLAG_COUNT, COMMENT_TABLE.VOTE_COUNT, COMMENT_TABLE.AUTHOR_ID,
                    COMMENT_TABLE.ANSWER_ID);

            //Objects for mocked result
            final var accountRecordID1 = new AccountRecord(1, "password_1", AccountStatus.ACTIVE.toString().toUpperCase(Locale.US),
                    "Test Name1", "test_name1@some.com", "(123) 321 45 67", 0);
            final var commentRecordID1 = new CommentRecord(1, "Some comment text.",
                    Timestamp.valueOf(LocalDateTime.now()), 0, 0, 1, 1);
            final var commentRecordID777 = new CommentRecord(777, "New text",
                    Timestamp.valueOf(LocalDateTime.now()), 0, 0, 1, 1);
            final var commentRecordID888 = new CommentRecord(888, "New text",
                    Timestamp.valueOf(LocalDateTime.now()), 0, 0, 1, 1);

            //Stipulations for returning different results
            if (sql.startsWith("INSERT INTO \"STACKOVERFLOW\".\"COMMENT\"") || sql.startsWith("SELECT \"STACKOVERFLOW\".\"COMMENT\"") && value[0].equals(1)) {
                resultComment.add(commentRecordID1);

                mock[0] = new MockResult(1, resultComment);
            }else if (sql.startsWith("SELECT \"STACKOVERFLOW\".\"ACCOUNT\"") && value[0].equals(1)) {
                resultAccount.add(accountRecordID1);

                mock[0] = new MockResult(1, resultAccount);
            }else if (sql.startsWith("UPDATE \"STACKOVERFLOW\".\"COMMENT\"") || (sql.startsWith("SELECT \"STACKOVERFLOW\".\"COMMENT\"") && value[0].equals(777))) {
                resultComment.add(commentRecordID777);

                mock[0] = new MockResult(1, resultComment);
            }else if (sql.startsWith("SELECT \"STACKOVERFLOW\".\"COMMENT\"") && value[0].equals(888)) {
                resultComment.add(commentRecordID888);

                mock[0] = new MockResult(1, resultComment);
            }else if (sql.startsWith("DELETE") && value[0].equals(888)) {
                resultComment.add(null);

                mock[0] = new MockResult(1, resultComment);
            }else {

                mock[0] = new MockResult(1, resultComment);
            }

            return mock;
        }
    }
}
