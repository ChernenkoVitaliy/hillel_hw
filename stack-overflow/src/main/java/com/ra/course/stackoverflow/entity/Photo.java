package com.ra.course.stackoverflow.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Photo {

    private Long id;
    private final String photoPath;
    private final LocalDateTime creationDate;

    public Photo(Long id, String photoPath) {
        this.id = id;
        this.photoPath = photoPath;
        creationDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return id.equals(photo.id) &&
                photoPath.equals(photo.photoPath) &&
                creationDate.equals(photo.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, photoPath, creationDate);
    }
}
