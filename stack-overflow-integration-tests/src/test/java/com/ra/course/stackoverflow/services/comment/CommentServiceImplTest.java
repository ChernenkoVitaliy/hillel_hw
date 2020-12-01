package com.ra.course.stackoverflow.services.comment;

import com.ra.course.stackoverflow.entity.*;
import com.ra.course.stackoverflow.entity.enums.AccountStatus;
import com.ra.course.stackoverflow.exception.CommentNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;


import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
public class CommentServiceImplTest {
    private final long ID = 1L;
    private final Account account = createAccount(ID);
    private final Member author = new Member(account);
    private final Member admin = new Admin(account);
    private final Member moderator = new Moderator(account);
    private final Question question = createQuestion(ID);
    private final Answer answer = createAnswer(ID);
    private final Comment comment = createComment(ID);
    @Autowired
    private CommentService commentService;

    @Test
    public void whenAddCommentToAnswerAndAnswerExists_ThenReturnCommentWithId() {
        //when
        final var resultComment = commentService.addComment(answer, comment);

        //then
        assertTrue(resultComment.getId() > 0);
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

    private Answer createAnswer(final long id) {
        return new Answer(id,
                "Some answer text",
                author,
                question.getId());
    }

    private Comment createComment(final long id) {
        return new Comment(id,
                "Some comment text",
                answer.getId(),
                author);
    }
}
