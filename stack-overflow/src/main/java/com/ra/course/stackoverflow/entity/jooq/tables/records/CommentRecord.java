/*
 * This file is generated by jOOQ.
 */
package com.ra.course.stackoverflow.entity.jooq.tables.records;


import com.ra.course.stackoverflow.entity.jooq.tables.CommentTable;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.processing.Generated;
import java.sql.Timestamp;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CommentRecord extends UpdatableRecordImpl<CommentRecord> implements Record7<Integer, String, Timestamp, Integer, Integer, Integer, Integer> {

    private static final long serialVersionUID = -563969306;

    /**
     * Setter for <code>stackoverflow.comment.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>stackoverflow.comment.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>stackoverflow.comment.text</code>.
     */
    public void setText(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>stackoverflow.comment.text</code>.
     */
    public String getText() {
        return (String) get(1);
    }

    /**
     * Setter for <code>stackoverflow.comment.created</code>.
     */
    public void setCreated(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>stackoverflow.comment.created</code>.
     */
    public Timestamp getCreated() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>stackoverflow.comment.flag_count</code>.
     */
    public void setFlagCount(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>stackoverflow.comment.flag_count</code>.
     */
    public Integer getFlagCount() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>stackoverflow.comment.vote_count</code>.
     */
    public void setVoteCount(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>stackoverflow.comment.vote_count</code>.
     */
    public Integer getVoteCount() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>stackoverflow.comment.author_id</code>.
     */
    public void setAuthorId(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>stackoverflow.comment.author_id</code>.
     */
    public Integer getAuthorId() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>stackoverflow.comment.answer_id</code>.
     */
    public void setAnswerId(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>stackoverflow.comment.answer_id</code>.
     */
    public Integer getAnswerId() {
        return (Integer) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row7<Integer, String, Timestamp, Integer, Integer, Integer, Integer> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    @Override
    public Row7<Integer, String, Timestamp, Integer, Integer, Integer, Integer> valuesRow() {
        return (Row7) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return CommentTable.COMMENT_TABLE.ID;
    }

    @Override
    public Field<String> field2() {
        return CommentTable.COMMENT_TABLE.TEXT;
    }

    @Override
    public Field<Timestamp> field3() {
        return CommentTable.COMMENT_TABLE.CREATED;
    }

    @Override
    public Field<Integer> field4() {
        return CommentTable.COMMENT_TABLE.FLAG_COUNT;
    }

    @Override
    public Field<Integer> field5() {
        return CommentTable.COMMENT_TABLE.VOTE_COUNT;
    }

    @Override
    public Field<Integer> field6() {
        return CommentTable.COMMENT_TABLE.AUTHOR_ID;
    }

    @Override
    public Field<Integer> field7() {
        return CommentTable.COMMENT_TABLE.ANSWER_ID;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getText();
    }

    @Override
    public Timestamp component3() {
        return getCreated();
    }

    @Override
    public Integer component4() {
        return getFlagCount();
    }

    @Override
    public Integer component5() {
        return getVoteCount();
    }

    @Override
    public Integer component6() {
        return getAuthorId();
    }

    @Override
    public Integer component7() {
        return getAnswerId();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getText();
    }

    @Override
    public Timestamp value3() {
        return getCreated();
    }

    @Override
    public Integer value4() {
        return getFlagCount();
    }

    @Override
    public Integer value5() {
        return getVoteCount();
    }

    @Override
    public Integer value6() {
        return getAuthorId();
    }

    @Override
    public Integer value7() {
        return getAnswerId();
    }

    @Override
    public CommentRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public CommentRecord value2(String value) {
        setText(value);
        return this;
    }

    @Override
    public CommentRecord value3(Timestamp value) {
        setCreated(value);
        return this;
    }

    @Override
    public CommentRecord value4(Integer value) {
        setFlagCount(value);
        return this;
    }

    @Override
    public CommentRecord value5(Integer value) {
        setVoteCount(value);
        return this;
    }

    @Override
    public CommentRecord value6(Integer value) {
        setAuthorId(value);
        return this;
    }

    @Override
    public CommentRecord value7(Integer value) {
        setAnswerId(value);
        return this;
    }

    @Override
    public CommentRecord values(Integer value1, String value2, Timestamp value3, Integer value4, Integer value5, Integer value6, Integer value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached CommentRecord
     */
    public CommentRecord() {
        super(CommentTable.COMMENT_TABLE);
    }

    /**
     * Create a detached, initialised CommentRecord
     */
    public CommentRecord(Integer id, String text, Timestamp created, Integer flagCount, Integer voteCount, Integer authorId, Integer answerId) {
        super(CommentTable.COMMENT_TABLE);

        set(0, id);
        set(1, text);
        set(2, created);
        set(3, flagCount);
        set(4, voteCount);
        set(5, authorId);
        set(6, answerId);
    }
}
