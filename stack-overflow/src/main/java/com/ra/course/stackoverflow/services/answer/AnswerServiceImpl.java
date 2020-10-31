package com.ra.course.stackoverflow.services.answer;

import com.ra.course.stackoverflow.dao.AnswerDao;
import com.ra.course.stackoverflow.dao.QuestionDao;
import com.ra.course.stackoverflow.entity.*;
import com.ra.course.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.stackoverflow.exception.AnswerNotFoundException;
import com.ra.course.stackoverflow.exception.QuestionNotFoundException;
import com.ra.course.stackoverflow.exception.QuestionStatusException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AnswerServiceImpl implements AnswerService {

    private static final transient String ANSWER_ERROR = "Answer must not be null.";
    private static final transient String QUESTION_ERROR = "Question must not be null.";
    private static final transient String MEMBER_ERROR = "Member must not be null.";
    private final transient AnswerDao answerDao;
    private final transient QuestionDao questionDao;

    public AnswerServiceImpl(final AnswerDao answerDao, final QuestionDao questionDao) {
        this.answerDao = answerDao;
        this.questionDao = questionDao;
    }

    /**Members should be able to add an answer to an open question.**/
    @Override
    public Answer addAnswer(final Answer answer, final Question question) {
        Objects.requireNonNull(answer, ANSWER_ERROR);
        Objects.requireNonNull(question, QUESTION_ERROR);

        if (question.getStatus().equals(QuestionStatus.OPEN)) {

            final var questionFromDB = questionDao.getById(question.getId())
                    .orElseThrow(() -> new QuestionNotFoundException("Question not found in DB."));

            questionFromDB.addAnswer(answer);
            questionDao.update(questionFromDB);
            answerDao.save(answer);
            return answer;
        }else {
            throw new QuestionStatusException("Can't add answer to not open question.");
        }
    }

    /**Member can delete own answer;
     * Moderator can delete answers;
     * Admin can delete answers;**/
    @Override
    public boolean deleteAnswer(final Member member, final Answer answer) {
        Objects.requireNonNull(member, MEMBER_ERROR);
        Objects.requireNonNull(answer, ANSWER_ERROR);

        if (answer.getAuthor().equals(member) || member.getClass() == Admin.class
                || member.getClass() == Moderator.class) {

            final var answerFromDB= answerDao.getById(answer.getId())
                    .orElseThrow(() -> new AnswerNotFoundException("Can't delete. Answer not found in DB."));

            final var questionFromDb = questionDao.getByAnswer(answer);
            questionFromDb.getAnswers().remove(answer);
            questionDao.update(questionFromDb);

            return answerDao.delete(answerFromDB);
        }

        return false;
    }
}
