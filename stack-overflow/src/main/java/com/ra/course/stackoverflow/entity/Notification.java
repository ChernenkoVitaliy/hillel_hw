package com.ra.course.stackoverflow.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Notification {

    private Long id;
    private final LocalDateTime createdOn;
    private final String content;

    public Notification(Long id, String content) {
        this.id = id;
        this.content = content;
        createdOn = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public String getContent() {
        return content;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return id.equals(that.id) &&
                createdOn.equals(that.createdOn) &&
                content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdOn, content);
    }
}
