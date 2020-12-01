package com.ra.course.stackoverflow.dao.impl;

import com.ra.course.stackoverflow.dao.AccountDao;
import com.ra.course.stackoverflow.dao.QuestionDao;
import com.ra.course.stackoverflow.entity.Answer;
import com.ra.course.stackoverflow.entity.Member;
import com.ra.course.stackoverflow.entity.Question;
import com.ra.course.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.stackoverflow.entity.jooq.tables.records.QuestionRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ra.course.stackoverflow.entity.jooq.tables.AnswerTable.ANSWER_TABLE;
import static com.ra.course.stackoverflow.entity.jooq.tables.QuestionTable.QUESTION_TABLE;

@Repository
public class QuestionDaoImpl implements QuestionDao {

    private final transient DSLContext dslContext;
    private final transient AccountDao accountDao;


    public QuestionDaoImpl(final DSLContext dslContext, final AccountDao accountDao) {
        this.dslContext = dslContext;
        this.accountDao = accountDao;
    }


    @Override
    public Optional<Question> getById(final long id) {

        final var recordQuestion = dslContext.fetchOne(QUESTION_TABLE, QUESTION_TABLE.ID.eq((int)id));

        if (recordQuestion == null) {
            return Optional.empty();
        }
        return Optional.of(mapperQuestion(recordQuestion));
    }

    @Override
    public Question save(final Question question) {

        final var questionResult = dslContext.insertInto(QUESTION_TABLE, QUESTION_TABLE.TITLE, QUESTION_TABLE.DESCRIPTION, QUESTION_TABLE.VIEW_COUNT,
                QUESTION_TABLE.VOTE_COUNT, QUESTION_TABLE.CREATED, QUESTION_TABLE.UPDATED, QUESTION_TABLE.STATUS,
                QUESTION_TABLE.CLOSING_REMARK, QUESTION_TABLE.AUTHOR_ID)
                .values(question.getTitle(), question.getDescription(), question.getViewCount(),
                        question.getVoteCount(), Timestamp.valueOf(question.getCreated()), Timestamp.valueOf(question.getUpdated()),
                        question.getStatus().toString().toUpperCase(Locale.US), question.getClosingRemark().toString().toUpperCase(Locale.US),
                        question.getAuthor().getAccount().getId().intValue())
                .returning()
                .fetchOne();

        return mapperQuestion(questionResult);
    }

    @Override
    public Question update(final Question question) {

        final var questionRecord = dslContext.update(QUESTION_TABLE)
                .set(QUESTION_TABLE.TITLE, question.getTitle())
                .set(QUESTION_TABLE.DESCRIPTION, question.getDescription())
                .set(QUESTION_TABLE.VIEW_COUNT, question.getViewCount())
                .set(QUESTION_TABLE.VOTE_COUNT, question.getVoteCount())
                .set(QUESTION_TABLE.CREATED, Timestamp.valueOf(question.getCreated()))
                .set(QUESTION_TABLE.UPDATED, Timestamp.valueOf(question.getUpdated()))
                .set(QUESTION_TABLE.STATUS, question.getStatus().toString().toUpperCase(Locale.US))
                .set(QUESTION_TABLE.CLOSING_REMARK, question.getClosingRemark().toString().toUpperCase(Locale.US))
                .set(QUESTION_TABLE.AUTHOR_ID, question.getAuthor().getAccount().getId().intValue())
                .where(QUESTION_TABLE.ID.eq(question.getId().intValue()))
                .returning()
                .fetchOne();

        return mapperQuestion(questionRecord);
    }

    @Override
    public boolean delete(final Question question) {

        final var questionFromDb = getById(question.getId());

        if (questionFromDb.isPresent()) {
            questionFromDb.get().setStatus(QuestionStatus.DELETED);
            update(questionFromDb.get());
            return true;
        }
        return false;
    }

    @Override
    public List<Question> getByAuthor(final Member author) {

        return dslContext.selectFrom(QUESTION_TABLE)
                .where(QUESTION_TABLE.AUTHOR_ID.eq(author.getAccount().getId().intValue()))
                .fetchStream()
                .map(this::mapperQuestion)
                .collect(Collectors.toList());
    }

    @Override
    public List<Question> getByTitle(final String searchPhrase) {

        return dslContext.selectFrom(QUESTION_TABLE)
                .where(QUESTION_TABLE.TITLE.contains(searchPhrase))
                .fetchStream()
                .map(this::mapperQuestion)
                .collect(Collectors.toList());
    }

    @Override
    public Question getByAnswer(final Answer answer) {

        final var questionId = dslContext.select(ANSWER_TABLE.QUESTION_ID)
                .from(ANSWER_TABLE)
                .where(ANSWER_TABLE.ID.eq(answer.getId().intValue()))
                .fetchOne()
                .getValue(ANSWER_TABLE.QUESTION_ID);

        final var resultQuestion = dslContext.fetchOne(QUESTION_TABLE, QUESTION_TABLE.ID.eq(questionId));

        return mapperQuestion(resultQuestion);
    }

    private Question mapperQuestion(final QuestionRecord record) {

        final Member member = new Member(accountDao.getById(record.getAuthorId()).get());

        return new Question((long)record.getId(),
                record.getTitle(),
                record.getDescription(),
                member);
    }

}
