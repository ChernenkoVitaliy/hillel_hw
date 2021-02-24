package com.ra.course.stackoverflow.dto;

import com.ra.course.stackoverflow.entity.Comment;
import com.ra.course.stackoverflow.entity.Member;
import com.ra.course.stackoverflow.entity.Photo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AnswerDto {

    private Long id;
    private String answerText;
    private boolean accepted;
    private int voteCount;
    private int flagCount;
    private final LocalDateTime created;
    private final Member author;
    private final List<Comment> comments;
    private final List<Photo> photos;
    private final Long questionID;

    public AnswerDto(Long id, String answerText, Member author, Long questionID) {
        this.id = id;
        this.answerText = answerText;
        this.author = author;
        this.questionID = questionID;
        created = LocalDateTime.now();
        comments = new ArrayList<>();
        photos = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getFlagCount() {
        return flagCount;
    }

    public void setFlagCount(int flagCount) {
        this.flagCount = flagCount;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public Member getAuthor() {
        return author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public Long getQuestionID() {
        return questionID;
    }
}
