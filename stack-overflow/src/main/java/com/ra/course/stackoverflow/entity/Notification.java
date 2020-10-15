package com.ra.course.stackoverflow.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Notification {

    private final long id;
    private final LocalDateTime createdOn;
    private final String content;

    public Notification(long id, String content) {
        this.id = id;
        this.content = content;
        createdOn = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return id == that.id &&
                Objects.equals(createdOn, that.createdOn) &&
                Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdOn, content);
    }
}
