package com.ra.course.stackoverflow.dao.impl;

import com.ra.course.stackoverflow.dao.CommentDao;
import com.ra.course.stackoverflow.entity.*;
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
public class CommentDaoImplTest {
    private final long ID = 1L;
    private final Account account = createAccount(ID);
    private final Member author = new Member(account);
    private final Question question = createQuestion(ID);
    private final Answer answer = createAnswer(ID);
    private final Comment comment = createComment(ID);
    @Autowired
    private CommentDao commentDao;


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
        //when
        comment.setText("New text");
        final var result = commentDao.update(comment);

        //then
        assertEquals(comment.getText(), result.getText());
    }

    @Test
    @Rollback
    public void whenDeleteComment_andCommentExists_ThenReturnTrue() {
        //when
        final var result = commentDao.delete(comment);

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
                "John Smith",
                "john_smith@some.com",
                "(123) 645 77 99");
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

    private Comment createComment(final long id) {
        return new Comment(id,
                "Some comment text",
                answer.getId(),
                author);
    }
}
