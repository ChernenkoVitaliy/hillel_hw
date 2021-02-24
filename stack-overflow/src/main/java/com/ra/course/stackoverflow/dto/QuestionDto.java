package com.ra.course.stackoverflow.dto;

import com.ra.course.stackoverflow.entity.*;
import com.ra.course.stackoverflow.entity.enums.QuestionClosingRemark;
import com.ra.course.stackoverflow.entity.enums.QuestionStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class QuestionDto {

    private Long id;
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


    public QuestionDto(Long id, String title, String description, Member author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        created = LocalDateTime.now();
        updated = LocalDateTime.now();
        answers = new ArrayList<>();
        photos = new ArrayList<>();
        tags = new ArrayList<>();
        status = QuestionStatus.CREATED;
        closingRemark = QuestionClosingRemark.NEW;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public QuestionStatus getStatus() {
        return status;
    }

    public void setStatus(QuestionStatus status) {
        this.status = status;
    }

    public QuestionClosingRemark getClosingRemark() {
        return closingRemark;
    }

    public void setClosingRemark(QuestionClosingRemark closingRemark) {
        this.closingRemark = closingRemark;
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

    public void setBounty(Bounty bounty) {
        this.bounty = bounty;
    }
}
