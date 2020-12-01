/*
 * This file is generated by jOOQ.
 */
package com.ra.course.stackoverflow.entity.jooq;


import com.ra.course.stackoverflow.entity.jooq.tables.AccountTable;
import com.ra.course.stackoverflow.entity.jooq.tables.AnswerTable;
import com.ra.course.stackoverflow.entity.jooq.tables.CommentTable;
import com.ra.course.stackoverflow.entity.jooq.tables.QuestionTable;

import javax.annotation.processing.Generated;


/**
 * Convenience access to all tables in stackoverflow
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>stackoverflow.account</code>.
     */
    public static final AccountTable ACCOUNT_TABLE = AccountTable.ACCOUNT_TABLE;

    /**
     * The table <code>stackoverflow.answer</code>.
     */
    public static final AnswerTable ANSWER_TABLE = AnswerTable.ANSWER_TABLE;

    /**
     * The table <code>stackoverflow.comment</code>.
     */
    public static final CommentTable COMMENT_TABLE = CommentTable.COMMENT_TABLE;

    /**
     * The table <code>stackoverflow.question</code>.
     */
    public static final QuestionTable QUESTION_TABLE = QuestionTable.QUESTION_TABLE;
}
