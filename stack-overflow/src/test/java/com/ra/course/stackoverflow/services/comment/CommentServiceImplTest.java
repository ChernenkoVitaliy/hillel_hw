package com.ra.course.stackoverflow.services.comment;

import com.ra.course.stackoverflow.dao.AnswerDao;
import com.ra.course.stackoverflow.dao.CommentDao;
import com.ra.course.stackoverflow.entity.*;
import com.ra.course.stackoverflow.entity.enums.AccountStatus;
import com.ra.course.stackoverflow.exception.AnswerNotFoundException;
import com.ra.course.stackoverflow.exception.CommentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CommentServiceImplTest {
    private final long ID = 1L;
    private final Account account = createAccount(ID);
    private final Member author = new Member(account);
    private final Answer answer = createAnswer(ID);
    private final Comment comment = createComment(ID);
    private final Admin admin = new Admin(createAccount(2L));
    private final Moderator moderator = new Moderator(createAccount(3L));
    private CommentService commentService;
    private final CommentDao commentDao = mock(CommentDao.class);
    private final AnswerDao answerDao = mock(AnswerDao.class);

    @BeforeEach
    public void setUp(){
        when(answerDao.getById(ID)).thenReturn(Optional.of(answer));
        when(commentDao.getById(ID)).thenReturn(Optional.of(comment));
        when(commentDao.delete(comment)).thenReturn(true);
        commentService = new CommentServiceImpl(commentDao, answerDao);
    }

    @Test // 'addComment' it's a name of method
    public void addCommentMethodShouldThrowExceptionWhenAnswerIsNull() {
        //then
        assertThatThrownBy(() -> commentService.addComment(null, comment))
                    .isInstanceOf(NullPointerException.class);
    }

    @Test // 'addComment' it's a name of method
    public void addCommentMethodShouldThrowExceptionWhenCommentIsNull() {
        //then
        assertThatThrownBy(() -> commentService.addComment(answer, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test // 'addComment' it's a name of method
    public void addCommentMethodShouldThrowExceptionWhenAnswerNotExistsInDB() {
        when(answerDao.getById(ID)).thenReturn(Optional.empty());
        //then
        assertThatThrownBy(() -> commentService.addComment(answer, comment))
                .isInstanceOf(AnswerNotFoundException.class);
    }

    @Test
    public void whenAddCommentToAnswerAndAnswerExists_ThenReturnCommentWithId() {
        when(answerDao.getById(ID)).thenReturn(Optional.of(answer));
        //when
        final var resultComment = commentService.addComment(answer, comment);

        //then
        assertThat(resultComment.getId()).isEqualTo(ID);
    }

    @Test // 'deleteComment' it's a name of method
    public void deleteCommentMethod_ShouldThrowException_WhenMemberIsNull() {
        //then
        assertThatThrownBy(() -> commentService.deleteComment(null, comment))
                .isInstanceOf(NullPointerException.class);
    }

    @Test // 'deleteComment' it's name of method
    public void deleteCommentMethod_ShouldThrowException_WhenCommentIsNull() {
        //then
        assertThatThrownBy(() -> commentService.deleteComment(author, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenMemberDeleteOwnComment_AndCommentExists_ThenReturnTrue() {
        //when
        final var result = commentService.deleteComment(author, comment);

        //then
        assertTrue(result);
    }

    @Test
    public void whenAdminDeleteComment_AndCommentExists_ThenReturnTrue() {
        //when
        final var result = commentService.deleteComment(admin, comment);

        //then
        assertTrue(result);
    }

    @Test
    public void whenModeratorDeleteComment_AndCommentExists_ThenReturnTrue() {
        //when
        final var result = commentService.deleteComment(moderator, comment);

        //then
        assertTrue(result);
    }

    @Test
    public void whenDeleteComment_AndCommentNotExistsInDB_ThenThrownException() {
        final var notExistComment = createComment(666L);
        when(commentDao.getById(notExistComment.getId())).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> commentService.deleteComment(admin, notExistComment))
                .isInstanceOf(CommentNotFoundException.class);
    }

    @Test
    public void whenAnotherMemberTryDeleteComment_ThenReturnFalse() {
        //given
        final var anotherMember = new Member(createAccount(666L));

        //when
        final var result = commentService.deleteComment(anotherMember, comment);

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

    private Answer createAnswer(final long id) {
        return new Answer(id,
                "Some answer text",
                author, 1L);
    }

    private Comment createComment(final long id) {
        return new Comment(id,
                "Some comment text",
                1L,
                author);
    }
}
