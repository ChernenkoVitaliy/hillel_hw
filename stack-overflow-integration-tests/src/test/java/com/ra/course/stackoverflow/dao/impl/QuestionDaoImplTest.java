package com.ra.course.stackoverflow.dao.impl;

import com.ra.course.stackoverflow.dao.QuestionDao;
import com.ra.course.stackoverflow.entity.Account;
import com.ra.course.stackoverflow.entity.Answer;
import com.ra.course.stackoverflow.entity.Member;
import com.ra.course.stackoverflow.entity.Question;
import com.ra.course.stackoverflow.entity.enums.AccountStatus;
import com.ra.course.stackoverflow.entity.enums.QuestionStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
public class QuestionDaoImplTest {
    private final long ID = 1L;
    private final Account account = createAccount(ID);
    private final Member author = new Member(account);
    private final Question question = createQuestion(ID);
    private final Answer answer = createAnswer(ID);
    @Autowired
    private QuestionDao questionDao;


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
        //when
        question.setTitle("New title.");
        final var result = questionDao.update(question);

        //then
        assertEquals(question.getTitle(), result.getTitle());
    }

    @Test
    public void whenDeleteQuestion_andQuestionExists_ThenReturnTrue() {
        //when
        final var result = questionDao.delete(question);

        //then
        assertTrue(result);
    }

    @Test
    public void whenDeleteQuestion_andQuestionExists_ThenSetQuestionStatusDELETED() {
        //when
        questionDao.delete(question);
        final var result =  questionDao.getById(question.getId()).get();

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
                "John Smith",
                "john_smith@some.com",
                "(123) 456 77 88");
    }

    private Question createQuestion(final long id) {
        return new Question(id,
                "Some question title.",
                "Some question description...",
                author);
    }

    private Answer createAnswer(final long id) {
        return new Answer(id,
                "Some answer text",
                author,
                question.getId());
    }
}
