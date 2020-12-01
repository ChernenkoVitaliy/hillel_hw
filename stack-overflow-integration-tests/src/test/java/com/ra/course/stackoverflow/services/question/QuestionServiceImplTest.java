package com.ra.course.stackoverflow.services.question;

import com.ra.course.stackoverflow.dao.QuestionDao;
import com.ra.course.stackoverflow.entity.*;
import com.ra.course.stackoverflow.entity.enums.AccountStatus;
import com.ra.course.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.stackoverflow.exception.QuestionNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
public class QuestionServiceImplTest {
    private final long ID = 1L;
    private final Account account = createAccount(ID);
    private final Member author = new Member(account);
    private final Member moderator = new Moderator(account);
    private final Member admin = new Admin(account);
    private final Question question = createQuestion(ID);
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionDao questionDao;


    @Test
    public void whenAddNewQuestion_ThenReturnNewQuestionWithId() {
        //when
        final var resultQuestion = questionService.addQuestion(question);

        //then
        assertThat(resultQuestion.getId()).isEqualTo(ID);
    }

    @Test
    public void deleteQuestionMethod_ShouldThrowException_WhenQuestionNotExistsInDB() {
        //given
        final var notExistQuestion = createQuestion(666L);

        //then
        assertThatThrownBy(() -> questionService.deleteQuestion(author, notExistQuestion))
                .isInstanceOf(QuestionNotFoundException.class);
    }

    @Test
    public void whenMemberDeleteOwnQuestion_AndQuestionExist_ThenReturnTrue() {
        //when
        final var result = questionService.deleteQuestion(author, question);

        //then
        assertTrue(result);
    }

    @Test
    public void whenModeratorDeleteQuestion_AndQuestionExist_ThenReturnTrue() {
        //when
        final var result = questionService.deleteQuestion(moderator, question);

        //then
        assertTrue(result);
    }

    @Test
    public void whenAdminDeleteQuestion_AndQuestionExists_ThenReturnTrue() {
        //when
        final var result = questionService.deleteQuestion(admin, question);

        //then
        assertTrue(result);
    }

    @Test
    public void whenAnotherMemberTryToDeleteQuestion_ThenReturnFalse() {
        //given
        final Member anotherMember = new Member(createAccount(2L));

        //when
        final var result = questionService.deleteQuestion(anotherMember, question);

        //then
        assertFalse(result);
    }

    @Test
    @Rollback
    public void whenMemberUndeleteOwnQuestion_AndQuestionExists_ThenReturnQuestionWithStatusOpen() {
        //given
        questionService.deleteQuestion(admin, question);

        //when
        final var questionResult = questionService.undeleteQuestion(author, question);

        //then
        assertEquals(QuestionStatus.OPEN, questionResult.getStatus());
    }

    @Test
    public void whenAdminOpenQuestion_AndQuestionExist_ThenReturnQuestionWithStatusOpen() {
        //given
        question.setStatus(QuestionStatus.CLOSED);
        questionDao.update(question);

        //when
        final var resultQuestion = questionService.openQuestion(admin, question);

        //then
        assertEquals(QuestionStatus.OPEN, resultQuestion.getStatus());
    }

    @Test
    @Rollback
    public void whenModeratorCloseQuestion_AndQuestionExist_ThenReturnQuestionWhitStatusClosed() {
        //given
        question.setStatus(QuestionStatus.OPEN);
        questionDao.update(question);

        //when
        final var resultQuestion = questionService.closeQuestion(moderator, question);

        //then
        assertEquals(QuestionStatus.CLOSED, resultQuestion.getStatus());
    }

    @Test
    public void whenMemberTryToCloseQuestionWithStatusThatNotOpenOrOnHold_ThenReturnQuestionWithoutAnyChanges() {
        //when
        final var result = questionService.closeQuestion(admin, question);

        //then
        assertEquals(question, result);
    }


    private Account createAccount(final long id) {
        return new Account(id,
                "password",
                AccountStatus.ACTIVE,
                "John Smith",
                "john_smith@some.com",
                "(123) 456 77 55");
    }

    private Question createQuestion(final long id) {
        return new Question(id,
                "Some question title.",
                "Some question description...",
                author);
    }
}
