package com.ra.course.stackoverflow.dao.impl;

import com.ra.course.stackoverflow.dao.AccountDao;
import com.ra.course.stackoverflow.dao.CommentDao;
import com.ra.course.stackoverflow.entity.Answer;
import com.ra.course.stackoverflow.entity.Comment;
import com.ra.course.stackoverflow.entity.Member;
import com.ra.course.stackoverflow.entity.jooq.tables.records.CommentRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ra.course.stackoverflow.entity.jooq.tables.CommentTable.COMMENT_TABLE;

@Repository
public class CommentDaoImpl implements CommentDao {

    private final transient DSLContext dslContext;
    private final transient AccountDao accountDao;

    public CommentDaoImpl(final DSLContext dslContext, final AccountDao accountDao) {
        this.dslContext = dslContext;
        this.accountDao = accountDao;
    }

    @Override
    public Optional<Comment> getById(final long id) {

        final var recordComment = dslContext.fetchOne(COMMENT_TABLE, COMMENT_TABLE.ID.eq((int)id));

        if (recordComment == null) {
            return Optional.empty();
        }
        return Optional.of(mapperComment(recordComment));
    }

    @Override
    public Comment save(final Comment comment) {

        final var commentRecord = dslContext.insertInto(COMMENT_TABLE, COMMENT_TABLE.TEXT,
                COMMENT_TABLE.CREATED, COMMENT_TABLE.FLAG_COUNT, COMMENT_TABLE.VOTE_COUNT, COMMENT_TABLE.AUTHOR_ID,
                COMMENT_TABLE.ANSWER_ID)
                .values(comment.getText(), Timestamp.valueOf(comment.getCreated()), comment.getFlagCount(),
                        comment.getVoteCount(), comment.getAuthor().getAccount().getId().intValue(),
                        comment.getAnswerID().intValue())
                .returning()
                .fetchOne();
        return mapperComment(commentRecord);
    }

    @Override
    public Comment update(final Comment comment) {

        final var commentRecord = dslContext.update(COMMENT_TABLE)
                .set(COMMENT_TABLE.TEXT, comment.getText())
                .set(COMMENT_TABLE.FLAG_COUNT, comment.getFlagCount())
                .set(COMMENT_TABLE.VOTE_COUNT, comment.getVoteCount())
                .where(COMMENT_TABLE.ID.eq(comment.getId().intValue()))
                .returning()
                .fetchOne();

        return mapperComment(commentRecord);
    }

    @Override
    public boolean delete(final Comment comment) {

        final var commentFromDb = getById(comment.getId());

        if (commentFromDb.isPresent()) {
            dslContext.delete(COMMENT_TABLE)
                    .where(COMMENT_TABLE.ID.eq(comment.getId().intValue()))
                    .execute();
            return true;
        }
        return false;
    }

    @Override
    public List<Comment> getByAnswer(final Answer answer) {
        return dslContext.selectFrom(COMMENT_TABLE)
                .where(COMMENT_TABLE.ANSWER_ID.eq(answer.getId().intValue()))
                .fetchStream()
                .map(this::mapperComment)
                .collect(Collectors.toList());
    }

    private Comment mapperComment(final CommentRecord record) {

        final Member author = new Member(accountDao.getById(record.getAuthorId().longValue()).get());
        return new Comment(record.getId().longValue(),
                record.getText(),
                record.getAnswerId().longValue(),
                author);
    }
}
