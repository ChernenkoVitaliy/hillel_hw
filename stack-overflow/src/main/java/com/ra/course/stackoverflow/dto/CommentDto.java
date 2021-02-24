package com.ra.course.stackoverflow.dto;

import com.ra.course.stackoverflow.entity.Member;

import java.time.LocalDateTime;

public class CommentDto {
    private Long id;
    private String text;
    private final LocalDateTime created;
    private int flagCount;
    private int voteCount;
    private final Long answerID;
    private final Member author;

    public CommentDto(Long id, String text, Long answerID, Member author) {
        this.id = id;
        this.text = text;
        this.answerID = answerID;
        this.author = author;
        created = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public int getFlagCount() {
        return flagCount;
    }

    public void setFlagCount(int flagCount) {
        this.flagCount = flagCount;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public Long getAnswerID() {
        return answerID;
    }

    public Member getAuthor() {
        return author;
    }
}
