package com.ra.course.stackoverflow.services.question;

import com.ra.course.stackoverflow.dao.QuestionDao;
import com.ra.course.stackoverflow.entity.Admin;
import com.ra.course.stackoverflow.entity.Member;
import com.ra.course.stackoverflow.entity.Moderator;
import com.ra.course.stackoverflow.entity.Question;
import com.ra.course.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.stackoverflow.exception.QuestionNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class QuestionServiceImpl implements QuestionService{

    private final transient QuestionDao questionDao;
    private static final transient String QUESTION_ERROR = "Question must not be null.";
    private static final transient String MEMBER_ERROR = "Member must not be null.";

    public QuestionServiceImpl(final QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    /**Members should be able to post new questions;**/
    @Override
    public Question addQuestion(final Question question) {
        Objects.requireNonNull(question, QUESTION_ERROR);
        questionDao.save(question);

        return question;
    }

    /**Member can delete own question;
     * Moderator can delete any question;
     * Admin can delete any question;**/
    @Override
    public boolean deleteQuestion(final Member member, final Question question) {
        Objects.requireNonNull(member, MEMBER_ERROR);
        Objects.requireNonNull(question, QUESTION_ERROR);

        if (question.getAuthor().equals(member) || member.getClass() == Admin.class
                || member.getClass() == Moderator.class) {

            final var questionFromDB= questionDao.getById(question.getId())
                    .orElseThrow(() -> new QuestionNotFoundException("Can't delete. Question not found in DB."));

            questionFromDB.setStatus(QuestionStatus.DELETED);
            question.setStatus(QuestionStatus.DELETED);
            questionDao.update(questionFromDB);
            return true;
        }

        return false;
    }

    /**Member can undelete own question;
     * Moderator can undelete any question;
     * Admin can undelete any question;**/
    @Override
    public Question undeleteQuestion(final Member member, final Question question) {
        Objects.requireNonNull(member, MEMBER_ERROR);
        Objects.requireNonNull(question, QUESTION_ERROR);

        if ((question.getAuthor().equals(member) || member.getClass() == Admin.class
                || member.getClass() == Moderator.class) && question.getStatus().equals(QuestionStatus.DELETED)) {

            final var questionFromDB = questionDao.getById(question.getId())
                    .orElseThrow(() -> new QuestionNotFoundException("Can't undelete question. Question not found in DB."));

            question.setStatus(QuestionStatus.OPEN);
            questionFromDB.setStatus(QuestionStatus.OPEN);
            questionDao.update(questionFromDB);

            return question;
        }

        return question;
    }

    /**Moderators can reopen any question;
     * Admins can reopen any question;**/
    @Override
    public Question openQuestion(final Member member, final Question question) {
        Objects.requireNonNull(member, MEMBER_ERROR);
        Objects.requireNonNull(question, QUESTION_ERROR);

        if ((member.getClass() == Moderator.class || member.getClass() == Admin.class)
        && question.getStatus().equals(QuestionStatus.CLOSED)) {

            final var questionFromDB = questionDao.getById(question.getId())
                    .orElseThrow(() -> new QuestionNotFoundException("Can't reopen question. Question not found in DB."));

            questionFromDB.setStatus(QuestionStatus.OPEN);
            question.setStatus(QuestionStatus.OPEN);
            questionDao.update(questionFromDB);

            return question;
        }

        return question;
    }

    /**Moderators can close any question;
     * Admins can close any question;**/
    @Override
    public Question closeQuestion(final Member member, final Question question) {
        Objects.requireNonNull(member, MEMBER_ERROR);
        Objects.requireNonNull(question, QUESTION_ERROR);

        if ((member.getClass() == Moderator.class || member.getClass() == Admin.class)
        &&question.getStatus() == QuestionStatus.OPEN || question.getStatus() == QuestionStatus.ON_HOLD) {

            final var questionFromDB = questionDao.getById(question.getId())
                    .orElseThrow(() -> new QuestionNotFoundException("Can't close question. Question not found in DB."));

            questionFromDB.setStatus(QuestionStatus.CLOSED);
            question.setStatus(QuestionStatus.CLOSED);
            questionDao.update(questionFromDB);

            return question;
        }
        return question;
    }
}
