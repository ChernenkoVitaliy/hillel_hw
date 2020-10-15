package com.ra.course.stackoverflow.entity;

import com.ra.course.stackoverflow.entity.enums.QuestionClosingRemark;
import com.ra.course.stackoverflow.entity.enums.QuestionStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Question {

    private final long id;
    private String title;
    private String description;
    private int viewCount;
    private int voteCount;
    private final LocalDateTime created;
    private LocalDateTime updated;
    private QuestionStatus status;
    private QuestionClosingRemark closingRemark;
    private final List<Answer> answers;
    private final List<Photo> photos;
    private final List<Tag> tags;
    private final Member author;
    private Bounty bounty;


    public Question(long id, String title, String description, Member author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        created = LocalDateTime.now();
        answers = new ArrayList<>();
        photos = new ArrayList<>();
        tags = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getViewCount() {
        return viewCount;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public QuestionStatus getStatus() {
        return status;
    }

    public QuestionClosingRemark getClosingRemark() {
        return closingRemark;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public Member getAuthor() {
        return author;
    }

    public Bounty getBounty() {
        return bounty;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public void setStatus(QuestionStatus status) {
        this.status = status;
    }

    public void setClosingRemark(QuestionClosingRemark closingRemark) {
        this.closingRemark = closingRemark;
    }

    public void setBounty(Bounty bounty) {
        this.bounty = bounty;
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    public boolean deleteAnswer(Answer answer) {
        return answers.remove(answer);
    }

    public void addPhoto(Photo photo) {
        photos.add(photo);
    }

    public boolean deletePhoto(Photo photo) {
        return photos.remove(photo);
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public boolean deleteTag(Tag tag) {
        return tags.remove(tag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return id == question.id &&
                viewCount == question.viewCount &&
                voteCount == question.voteCount &&
                title.equals(question.title) &&
                description.equals(question.description) &&
                created.equals(question.created) &&
                Objects.equals(updated, question.updated) &&
                status == question.status &&
                closingRemark == question.closingRemark &&
                author.equals(question.author) &&
                Objects.equals(bounty, question.bounty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, viewCount, voteCount, created, updated, status, closingRemark, author, bounty);
    }
}
