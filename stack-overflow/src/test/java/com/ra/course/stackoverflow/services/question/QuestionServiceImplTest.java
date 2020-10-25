package com.ra.course.stackoverflow.services.question;

import com.ra.course.stackoverflow.dao.QuestionDao;
import com.ra.course.stackoverflow.entity.*;
import com.ra.course.stackoverflow.entity.enums.AccountStatus;
import com.ra.course.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.stackoverflow.exception.QuestionNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QuestionServiceImplTest {
    private final long ID = 1L;
    private final Account account = createAccount(ID);
    private final Member author = new Member(account);
    private final Member moderator = new Moderator(createAccount(2L));
    private final Member admin = new Admin(createAccount(3L));
    private final Question question = createQuestion(ID);
    private QuestionService questionService;
    private final QuestionDao questionDao = mock(QuestionDao.class);

    @BeforeEach
    public void setUp() {
        questionService = new QuestionServiceImpl(questionDao);
    }

    @Test
    public void whenAddNewQuestion_ThenReturnNewQuestionWithId() {
        //when
        final var resultQuestion = questionService.addQuestion(question);

        //then
        assertThat(resultQuestion.getId()).isEqualTo(ID);
    }

    @Test //'addQuestion' it's name of method
    public void addQuestionMethod_ShouldThrowException_WhenQuestionIsNull() {
        //then
        assertThatThrownBy(() -> questionService.addQuestion(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test //'deleteQuestion' it's name of method
    public void deleteQuestionMethod_ShouldThrowException_WhenMemberIsNull() {
        //then
        assertThatThrownBy(() -> questionService.deleteQuestion(null, question))
                .isInstanceOf(NullPointerException.class);
    }

    @Test //'deleteQuestion' it's name of method
    public void deleteQuestionMethod_ShouldThrowException_WhenQuestionIsNull() {
        //then
        assertThatThrownBy(() -> questionService.deleteQuestion(author, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void deleteQuestionMethod_ShouldThrowException_WhenQuestionNotExistsInDB() {
        //given
        final var notExistQuestion = createQuestion(666L);
        when(questionDao.getById(666L)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> questionService.deleteQuestion(moderator, notExistQuestion))
                .isInstanceOf(QuestionNotFoundException.class);
    }

    @Test
    public void whenMemberDeleteOwnQuestion_AndQuestionExist_ThenReturnTrue() {
        when(questionDao.getById(ID)).thenReturn(Optional.of(question));

        //when
        final var result = questionService.deleteQuestion(author, question);

        //then
        assertTrue(result);
    }

    @Test
    public void whenModeratorDeleteQuestion_AndQuestionExist_ThenReturnTrue() {
        when(questionDao.getById(ID)).thenReturn(Optional.of(question));

        //when
        final var result = questionService.deleteQuestion(moderator, question);

        //then
        assertTrue(result);
    }

    @Test
    public void whenAdminDeleteQuestion_AndQuestionExists_ThenReturnTrue() {
        when(questionDao.getById(ID)).thenReturn(Optional.of(question));

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

    @Test //'undeleteQuestion' it's name of method
    public void undeleteQuestionMethod_ShouldThrowException_WhenMemberIsNull() {
        assertThatThrownBy(() -> questionService.undeleteQuestion(null, question))
                .isInstanceOf(NullPointerException.class);
    }

    @Test //'undeleteQuestion' it's name of method
    public void undeleteQuestionMethod_ShouldThrowException_WhenQuestionIsNull() {
        assertThatThrownBy(() -> questionService.undeleteQuestion(author, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenMemberUndeleteOwnQuestion_AndQuestionExists_ThenReturnQuestionWithStatusOpen() {
        //given
        question.setStatus(QuestionStatus.DELETED);
        when(questionDao.getById(ID)).thenReturn(Optional.of(question));

        //when
        final var questionResult = questionService.undeleteQuestion(author, question);

        //then
        assertEquals(QuestionStatus.OPEN, questionResult.getStatus());
    }

    @Test
    public void whenAdminUndeleteQuestion_AndQuestionExists_ThenReturnQuestionWithStatusOpen() {
        //given
        question.setStatus(QuestionStatus.DELETED);
        when(questionDao.getById(ID)).thenReturn(Optional.of(question));

        //when
        final var resultQuestion = questionService.undeleteQuestion(admin, question);

        //then
        assertEquals(QuestionStatus.OPEN, resultQuestion.getStatus());
    }

    @Test
    public void whenModeratorUndeleteQuestion_AndQuestionExists_ThenReturnQuestionWithStatusOpen() {
        //given
        question.setStatus(QuestionStatus.DELETED);
        when(questionDao.getById(ID)).thenReturn(Optional.of(question));

        //when
        final var resultQuestion = questionService.undeleteQuestion(moderator, question);

        //then
        assertEquals(QuestionStatus.OPEN, resultQuestion.getStatus());
    }

    @Test
    public void whenAnotherMemberTryToUndeleteQuestion_ThenReturnQuestionWithoutAnyChanges() {
        //given
        final var anotherMember = new Member(createAccount(2L));
        question.setStatus(QuestionStatus.DELETED);
        final var statusBefore = question.getStatus();

        //when
        final var statusAfter = questionService.undeleteQuestion(anotherMember, question);

        //then
        assertNotEquals(statusAfter, statusBefore);
    }

    @Test //only question with QuestionStatus.DELETED can be undeleted.
    public void whenTryToUndeleteQuestionWithStatusThatNotDeleted_ThenReturnQuestionWithoutAnyChanges() {
        //given
        final var statusBefore = question.getStatus();

        //when
        final  var statusAfter = questionService.undeleteQuestion(author, question).getStatus();

        //then
        assertEquals(statusAfter, statusBefore);
    }

    @Test
    public void whenTryToUndeleteQuestion_AndQuestionNotExist_ThrowException() {
        //given
        final var notExistQuestion = createQuestion(666L);
        notExistQuestion.setStatus(QuestionStatus.DELETED);
        when(questionDao.getById(666L)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> questionService.undeleteQuestion(admin, notExistQuestion))
                .isInstanceOf(QuestionNotFoundException.class);
    }

    @Test //'openQuestion' it's a name of method
    public void openQuestionMethod_ShouldThrowException_WhenMemberIsNull(){
        //then
        assertThatThrownBy(() -> questionService.openQuestion(null, question))
                .isInstanceOf(NullPointerException.class);
    }

    @Test //'openQuestion' it's a name of method
    public void openQuestionMethod_ShouldThrowException_WhenQuestionIsNull() {
        //then
        assertThatThrownBy(() -> questionService.openQuestion(author, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenModeratorOpenQuestion_AndQuestionExist_ThenReturnQuestionWhitStatusOpen() {
        //given
        question.setStatus(QuestionStatus.CLOSED);
        when(questionDao.getById(ID)).thenReturn(Optional.of(question));

        //when
        final var resultQuestion = questionService.openQuestion(moderator, question);

        //then
        assertEquals(QuestionStatus.OPEN, resultQuestion.getStatus());
    }

    @Test
    public void whenAdminOpenQuestion_AndQuestionExist_ThenReturnQuestionWithStatusOpen() {
        //given
        question.setStatus(QuestionStatus.CLOSED);
        when(questionDao.getById(ID)).thenReturn(Optional.of(question));

        //when
        final var resultQuestion = questionService.openQuestion(admin, question);

        //then
        assertEquals(QuestionStatus.OPEN, resultQuestion.getStatus());
    }

    @Test
    public void whenNotModeratorOrAdminTryToOpenClosedQuestion_ThenReturnQuestionWithoutAnyChanges() {
        //given
        question.setStatus(QuestionStatus.CLOSED);

        //when
        final var resultQuestion = questionService.openQuestion(author, question);

        //then
        assertEquals(question, resultQuestion);
    }

    @Test
    public void whenMemberTryOpenQuestionWithStatusThatNotClosed_ThenReturnQuestionWithoutAnyChanges() {
        //given
        question.setStatus(QuestionStatus.OPEN);

        //when
        final var result = questionService.openQuestion(admin, question);

        //then
        assertEquals(question, result);
    }

    @Test
    public void whenTryToOpenQuestionWitchNotExists_ThenThrowException() {
        //given
        final var notExistQuestion = createQuestion(666L);
        notExistQuestion.setStatus(QuestionStatus.CLOSED);
        when(questionDao.getById(666L)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> questionService.openQuestion(admin, notExistQuestion))
                .isInstanceOf(QuestionNotFoundException.class);
    }

    @Test //'closeQuestion' it's a name of method
    public void closeQuestionMethod_ShouldThrowException_WhenMemberIsNull(){
        //then
        assertThatThrownBy(() -> questionService.closeQuestion(null, question))
                .isInstanceOf(NullPointerException.class);
    }

    @Test //'closeQuestion' it's a name of method
    public void closeQuestionMethod_ShouldThrowException_WhenQuestionIsNull() {
        //then
        assertThatThrownBy(() -> questionService.closeQuestion(author, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenModeratorCloseQuestion_AndQuestionExist_ThenReturnQuestionWhitStatusClosed() {
        //given
        question.setStatus(QuestionStatus.OPEN);
        when(questionDao.getById(ID)).thenReturn(Optional.of(question));

        //when
        final var resultQuestion = questionService.closeQuestion(moderator, question);

        //then
        assertEquals(QuestionStatus.CLOSED, resultQuestion.getStatus());
    }

    @Test
    public void whenAdminCloseQuestion_AndQuestionExist_ThenReturnQuestionWithStatusClosed() {
        //given
        question.setStatus(QuestionStatus.OPEN);
        when(questionDao.getById(ID)).thenReturn(Optional.of(question));

        //when
        final var resultQuestion = questionService.closeQuestion(admin, question);

        //then
        assertEquals(QuestionStatus.CLOSED, resultQuestion.getStatus());
    }

    @Test
    public void whenNotModeratorOrAdminTryToCloseOpenedQuestion_ThenReturnQuestionWithoutAnyChanges() {
        //given
        question.setStatus(QuestionStatus.OPEN);

        //when
        final var resultQuestion = questionService.closeQuestion(author, question);

        //then
        assertEquals(question, resultQuestion);
    }

    @Test
    public void whenMemberTryToCloseQuestionWithStatusThatNotOpenOrOnHold_ThenReturnQuestionWithoutAnyChanges() {
        //given
        question.setStatus(QuestionStatus.CLOSED);

        //when
        final var result = questionService.closeQuestion(admin, question);

        //then
        assertEquals(question, result);
    }

    @Test
    public void whenTryToCloseQuestionWitchNotExists_ThenThrowException() {
        //given
        final var notExistQuestion = createQuestion(666L);
        notExistQuestion.setStatus(QuestionStatus.OPEN);
        when(questionDao.getById(666L)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> questionService.closeQuestion(admin, notExistQuestion))
                .isInstanceOf(QuestionNotFoundException.class);
    }

    private Account createAccount(final long id) {
        return new Account(id,
                "password",
                AccountStatus.ACTIVE,
                "Test John",
                "test_john@some.com",
                "(123)-321-45-67");
    }

    private Question createQuestion(final long id) {
        final var question =  new Question(id,
                "Some question title.",
                "Some question description",
                author);
        question.setStatus(QuestionStatus.OPEN);

        return question;
    }
}
