package com.ra.course.stackoverflow.dao.impl;

import com.ra.course.stackoverflow.dao.AccountDao;
import com.ra.course.stackoverflow.dao.AnswerDao;
import com.ra.course.stackoverflow.entity.*;
import com.ra.course.stackoverflow.entity.jooq.tables.records.AnswerRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ra.course.stackoverflow.entity.jooq.tables.AnswerTable.ANSWER_TABLE;

@Repository
public class AnswerDaoImpl implements AnswerDao {

    private final transient DSLContext dslContext;
    private final transient AccountDao accountDao;

    public AnswerDaoImpl(final DSLContext dslContext, final AccountDao accountDao) {
        this.dslContext = dslContext;
        this.accountDao = accountDao;
    }

    @Override
    public Optional<Answer> getById(final long id) {

        final var recordAnswer = dslContext.fetchOne(ANSWER_TABLE, ANSWER_TABLE.ID.eq((int)id));

        if (recordAnswer == null) {
            return Optional.empty();
        }
        return Optional.of(mapperAnswer(recordAnswer));
    }

    @Override
    public Answer save(final Answer answer) {

        final byte accepted = (byte) (answer.isAccepted() ? 1 : 0);
        final var answerResult = dslContext.insertInto(ANSWER_TABLE, ANSWER_TABLE.ANSWER_TEXT, ANSWER_TABLE.ACCEPTED,
                ANSWER_TABLE.VOTE_COUNT, ANSWER_TABLE.FLAG_COUNT, ANSWER_TABLE.CREATED, ANSWER_TABLE.AUTHOR_ID, ANSWER_TABLE.QUESTION_ID).
                values(answer.getAnswerText(), accepted, answer.getVoteCount(), answer.getFlagCount(),
                        Timestamp.valueOf(answer.getCreated()), answer.getAuthor().getAccount().getId().intValue(),
                        answer.getQuestionID().intValue())
                .returning()
                .fetchOne();
        return mapperAnswer(answerResult);
    }

    @Override
    public Answer update(final Answer answer) {

        final byte accepted = (byte) (answer.isAccepted() ? 1 : 0);
        final var answerRecord = dslContext.update(ANSWER_TABLE)
                .set(ANSWER_TABLE.ANSWER_TEXT, answer.getAnswerText())
                .set(ANSWER_TABLE.ACCEPTED, accepted)
                .set(ANSWER_TABLE.VOTE_COUNT, answer.getVoteCount())
                .set(ANSWER_TABLE.FLAG_COUNT, answer.getFlagCount())
                .where(ANSWER_TABLE.ID.eq(answer.getId().intValue()))
                .returning()
                .fetchOne();
        return mapperAnswer(answerRecord);
    }

    @Override
    public boolean delete(final Answer answer) {

        final var answerFromDB = getById(answer.getId());

        if (answerFromDB.isPresent()) {
            dslContext.delete(ANSWER_TABLE).where(ANSWER_TABLE.ID.eq(answer.getId().intValue()))
                    .execute();
            return true;
        }
        return false;
    }

    @Override
    public List<Answer> getByAuthor(final Member author) {

        return dslContext.selectFrom(ANSWER_TABLE)
                .where(ANSWER_TABLE.AUTHOR_ID.eq(author.getAccount().getId().intValue()))
                .fetchStream()
                .map(this::mapperAnswer)
                .collect(Collectors.toList());
    }

    @Override
    public List<Answer> getByQuestion(final Question question) {

        return dslContext.selectFrom(ANSWER_TABLE)
                .where(ANSWER_TABLE.QUESTION_ID.eq(question.getId().intValue()))
                .fetchStream()
                .map(this::mapperAnswer)
                .collect(Collectors.toList());
    }

    private Answer mapperAnswer(final AnswerRecord record) {
        final Member author = new Member(accountDao.getById(record.getAuthorId()).get());
        return new Answer(record.getId().longValue(),
                record.getAnswerText(),
                author,
                record.getQuestionId().longValue());
    }
}
