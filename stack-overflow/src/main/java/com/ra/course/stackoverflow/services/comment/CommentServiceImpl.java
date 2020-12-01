package com.ra.course.stackoverflow.services.comment;

import com.ra.course.stackoverflow.dao.AnswerDao;
import com.ra.course.stackoverflow.dao.CommentDao;
import com.ra.course.stackoverflow.entity.*;
import com.ra.course.stackoverflow.exception.AnswerNotFoundException;
import com.ra.course.stackoverflow.exception.CommentNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CommentServiceImpl implements CommentService {

    private static final transient String ANSWER_ERROR = "Answer must not be null.";
    private static final transient String COMMENT_ERROR = "Comment must not be null.";
    private static final transient String MEMBER_ERROR = "Member must not be null.";
    private final transient CommentDao commentDao;
    private final transient AnswerDao answerDao;

    public CommentServiceImpl(final CommentDao commentDao, final AnswerDao answerDao) {
        this.commentDao = commentDao;
        this.answerDao = answerDao;
    }

    /**Members can add comments to any answer.**/
    @Override
    public Comment addComment(final Answer answer, final Comment comment) {
        Objects.requireNonNull(answer, ANSWER_ERROR);
        Objects.requireNonNull(comment, COMMENT_ERROR);

        final var answerFromDB = answerDao.getById(answer.getId())
                .orElseThrow(() -> new AnswerNotFoundException("Answer not found in DB."));

        answerFromDB.addComment(comment);
        answerDao.update(answer);
        commentDao.save(comment);

        return comment;
    }

    /**Member can delete own comment;
     * Admin can delete any comment;
     * Moderator can delete any comment;**/
    @Override
    public boolean deleteComment(final Member member, final Comment comment) {
        Objects.requireNonNull(member, MEMBER_ERROR);
        Objects.requireNonNull(comment, COMMENT_ERROR);

        if (comment.getAuthor().equals(member) || member.getClass() == Admin.class
                || member.getClass() == Moderator.class) {

            final var commentFromDB= commentDao.getById(comment.getId())
                    .orElseThrow(() -> new CommentNotFoundException("Can't delete. Comment not found in DB."));

            final var answerFromDb = answerDao.getById(comment.getAnswerID())
                    .orElseThrow(() -> new AnswerNotFoundException("Answer not exists in DB."));
            answerFromDb.getComments().remove(comment);
            answerDao.update(answerFromDb);

            return commentDao.delete(commentFromDB);
        }

        return false;
    }
}
