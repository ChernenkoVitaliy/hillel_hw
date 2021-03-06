package com.ra.course.stackoverflow.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Answer {

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

    public Answer(Long id, String answerText, Member author, Long questionID) {
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

    public String getAnswerText() {
        return answerText;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public int getFlagCount() {
        return flagCount;
    }

    public Long getQuestionID() {
        return questionID;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public void setFlagCount(int flagCount) {
        this.flagCount = flagCount;
    }

    public void incrementVoteCount() {
        voteCount++;
    }

    public void incrementFlagCount() {
        flagCount++;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public boolean deleteComment(Comment comment) {
        return comments.remove(comment);
    }

    public void addPhoto(Photo photo) {
        photos.add(photo);
    }

    public boolean deletePhoto(Photo photo) {
        return photos.remove(photo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return accepted == answer.accepted &&
                voteCount == answer.voteCount &&
                flagCount == answer.flagCount &&
                id.equals(answer.id) &&
                answerText.equals(answer.answerText) &&
                created.equals(answer.created) &&
                author.equals(answer.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, answerText, accepted, voteCount, flagCount, created, author);
    }
}
