package com.ra.course.stackoverflow.dto;

import java.time.LocalDateTime;

public class NotificationDto {
    private Long id;
    private final LocalDateTime createdOn;
    private final String content;

    public NotificationDto(Long id, String content) {
        this.id = id;
        this.content = content;
        createdOn = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public String getContent() {
        return content;
    }
}
