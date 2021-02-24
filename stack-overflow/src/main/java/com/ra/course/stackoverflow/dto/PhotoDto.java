package com.ra.course.stackoverflow.dto;

import java.time.LocalDateTime;

public class PhotoDto {

    private Long id;
    private final String photoPath;
    private final LocalDateTime creationDate;

    public PhotoDto(Long id, String photoPath) {
        this.id = id;
        this.photoPath = photoPath;
        creationDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
}
