package com.ra.course.stackoverflow.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Comment {

    private final long id;
    private final String text;
    private final LocalDateTime created;
    private int flagCount;
    private int voteCount;
    private final Member author;

    public Comment(long id, String text, Member author) {
        this.id = id;
        this.text = text;
        this.author = author;
        created = LocalDateTime.now();
    }

    public long getId() {
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
        return id == comment.id &&
                flagCount == comment.flagCount &&
                voteCount == comment.voteCount &&
                text.equals(comment.text) &&
                created.equals(comment.created) &&
                author.equals(comment.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, created, flagCount, voteCount, author);
    }
}
