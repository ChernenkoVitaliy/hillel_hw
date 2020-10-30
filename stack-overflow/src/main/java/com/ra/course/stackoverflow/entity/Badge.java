package com.ra.course.stackoverflow.entity;

import java.util.Objects;

public class Badge {

    private Long id;
    private final String name;
    private final String description;

    public Badge(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Badge badge = (Badge) o;
        return id.equals(badge.id) &&
                name.equals(badge.name) &&
                description.equals(badge.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
