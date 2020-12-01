package com.ra.course.stackoverflow.dao.impl;

import com.ra.course.stackoverflow.dao.AnswerDao;
import com.ra.course.stackoverflow.entity.Account;
import com.ra.course.stackoverflow.entity.Answer;
import com.ra.course.stackoverflow.entity.Member;
import com.ra.course.stackoverflow.entity.Question;
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
public class AnswerDaoImplTest {
    private final long ID = 1L;
    private final Account account = createAccount(ID);
    private final Member author = new Member(account);
    private final Question question = createQuestion(ID);
    private final Answer answer = createAnswer(ID);
    @Autowired
    private AnswerDao answerDao;


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
        //when
        answer.setAnswerText("New answer text");
        final var result = answerDao.update(answer);

        //then
        assertEquals(answer.getAnswerText(), result.getAnswerText());
    }

    @Test
    @Rollback
    public void whenDeleteAnswer_andAnswerExists_ThenReturnTrue() {
        //when
        final var result = answerDao.delete(answer);

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
                "John Smith",
                "john_smith@some.com",
                "(123) 654 78 99");
    }

    private Question createQuestion(final long id) {
        return new Question(id,
                "Some question title",
                "Some question description",
                author);
    }

    private Answer createAnswer(final long id) {
        return new Answer(id,
                "Some answer text",
                author,
                question.getId());
    }
}
