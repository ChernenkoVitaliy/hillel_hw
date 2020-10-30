package com.ra.course.stackoverflow.services.answer;

import com.ra.course.stackoverflow.dao.AnswerDao;
import com.ra.course.stackoverflow.dao.QuestionDao;
import com.ra.course.stackoverflow.entity.*;
import com.ra.course.stackoverflow.entity.enums.AccountStatus;
import com.ra.course.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.stackoverflow.exception.AnswerNotFoundException;
import com.ra.course.stackoverflow.exception.QuestionNotFoundException;
import com.ra.course.stackoverflow.exception.QuestionStatusException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AnswerServiceImplTest {
    private final long ID = 1L;
    private final Account account = createAccount(ID);
    private final Member author = new Member(account);
    private final Admin admin = new Admin(createAccount(2L));
    private final Moderator moderator = new Moderator(createAccount(3L));
    private final Question question = createQuestion(ID);
    private final Answer answer = createAnswer(ID);
    private  final AnswerDao answerDao = mock(AnswerDao.class);
    private final QuestionDao questionDao = mock(QuestionDao.class);
    private AnswerServiceImpl answerService;

    @BeforeEach
    public void SetUp() {
        answerService = new AnswerServiceImpl(answerDao, questionDao);
        when(answerDao.getById(ID)).thenReturn(Optional.of(answer));
        when(questionDao.getByAnswer(answer)).thenReturn(question);
        when(answerDao.delete(answer)).thenReturn(true);
    }

    @Test //'addAnswer' it's a name of method
    public void addAnswerMethod_ShouldThrowException_WhenAnswerIsNull() {
        //then
        assertThatThrownBy(() -> answerService.addAnswer(null, question))
                .isInstanceOf(NullPointerException.class);
    }

    @Test// 'addAnswer' it's name of method
    public void addAnswerMethod_ShouldThrowException_WhenQuestionIsNull() {
        //then
        assertThatThrownBy(() -> answerService.addAnswer(answer, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenTryToAddAnswerToQuestion_ButQuestionNotExists_ThenThrowException() {
        //given
        when(questionDao.getById(1L)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> answerService.addAnswer(answer, question))
                .isInstanceOf(QuestionNotFoundException.class);
    }

    @Test
    public void whenTryAddAnswerToClosedQuestion_ThenThrowException() {
        //given
        question.setStatus(QuestionStatus.CLOSED);
        when(questionDao.getById(1L)).thenReturn(Optional.of(question));

        //then
        assertThatThrownBy(() -> answerService.addAnswer(answer, question))
                .isInstanceOf(QuestionStatusException.class);
    }

    @Test
    public void whenAddAnswerToOpenQuestion_ThenReturnAnswerWithId() {
        when(questionDao.getById(1L)).thenReturn(Optional.of(question));
        //when
        final var resultAnswer = answerService.addAnswer(answer, question);

        //then
        assertThat(resultAnswer.getId()).isEqualTo(ID);
    }

    /////////write tests for delete method

    @Test
    public void deleteAnswerMethod_ShouldThrowException_WhenMemberIsNull() {
        //then
        assertThatThrownBy(() -> answerService.deleteAnswer(null, answer))
                    .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void deleteAnswerMethod_ShouldThrowException_WhenAnswerIsNull() {
        //then
        assertThatThrownBy(() -> answerService.deleteAnswer(author, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenMemberDeleteOwnAnswer_AndAnswerExists_ThenReturnTrue() {
        //when
        final var result = answerService.deleteAnswer(author, answer);

        //then
        assertTrue(result);
    }

    @Test
    public void whenAdminDeleteAnswer_AndAnswerExists_ThenReturnTrue() {
        //when
        final var result = answerService.deleteAnswer(admin, answer);

        //then
        assertTrue(result);
    }

    @Test
    public void whenModeratorDeleteAnswer_AndAnswerExists_ThenReturnTrue() {
        //when
        final var result = answerService.deleteAnswer(moderator, answer);

        //then
        assertTrue(result);
    }

    @Test
    public void whenDeleteAnswer_AndAnswerNotExistsInDB_ThenThrownException() {
        final var notExistAnswer = createAnswer(666L);
        when(answerDao.getById(notExistAnswer.getId())).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> answerService.deleteAnswer(admin, notExistAnswer))
                .isInstanceOf(AnswerNotFoundException.class);
    }

    @Test
    public void whenAnotherMemberTryDeleteAnswer_ThenReturnFalse() {
        //given
        final var anotherMember = new Member(createAccount(666L));

        //when
        final var result = answerService.deleteAnswer(anotherMember, answer);

        //then
        assertFalse(result);
    }

    private Account createAccount(final long id) {
        return new Account(id,
                "password",
                AccountStatus.ACTIVE,
                "John Test",
                "john_test@some.com",
                "(123) 321 45 67");
    }

    private Question createQuestion(long id) {
        final var question =  new Question(id,
                "Some question title",
                "Some question description",
                author);
        question.setStatus(QuestionStatus.OPEN);

        return question;
    }

    private Answer createAnswer(final long id) {
        return new Answer(id,
                "Some answer text",
                author);
    }
}
