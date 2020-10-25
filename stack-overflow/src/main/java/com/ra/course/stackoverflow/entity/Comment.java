package com.ra.course.stackoverflow.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Comment {

    private Long id;
    private final String text;
    private final LocalDateTime created;
    private int flagCount;
    private int voteCount;
    private final Member author;

    public Comment(Long id, String text, Member author) {
        this.id = id;
        this.text = text;
        this.author = author;
        created = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public int getFlagCount() {
        return flagCount;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public Member getAuthor() {
        return author;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFlagCount(int flagCount) {
        this.flagCount = flagCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public void incrementFlagCount() {
        flagCount++;
    }

    public void incrementVoteCount() {
        voteCount++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return flagCount == comment.flagCount &&
                voteCount == comment.voteCount &&
                id.equals(comment.id) &&
                text.equals(comment.text) &&
                created.equals(comment.created) &&
                author.equals(comment.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, created, flagCount, voteCount, author);
    }
}
