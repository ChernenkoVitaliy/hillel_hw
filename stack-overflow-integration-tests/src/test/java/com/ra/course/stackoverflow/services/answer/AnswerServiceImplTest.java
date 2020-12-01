package com.ra.course.stackoverflow.services.answer;

import com.ra.course.stackoverflow.entity.*;
import com.ra.course.stackoverflow.entity.enums.AccountStatus;
import com.ra.course.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.stackoverflow.exception.AnswerNotFoundException;
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
public class AnswerServiceImplTest {
    private final long ID = 1L;
    private final Account account = createAccount(ID);
    private final Member author = new Member(account);
    private final Member admin = new Admin(account);
    private final Member moderator = new Moderator(account);
    private final Question question = createQuestion(ID);
    private final Answer answer = createAnswer(ID);
    @Autowired
    private AnswerService answerService;


    @Test
    public void whenAddAnswerToOpenQuestion_ThenReturnAnswerWithId() {
        //given
        question.setStatus(QuestionStatus.OPEN);

        //when
        final var resultAnswer = answerService.addAnswer(answer, question);

        //then
        assertTrue(resultAnswer.getId() > 0);
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
}
