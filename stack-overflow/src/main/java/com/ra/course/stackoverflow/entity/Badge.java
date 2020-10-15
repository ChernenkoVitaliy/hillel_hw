package com.ra.course.stackoverflow.entity;

import java.util.Objects;

public class Badge {

    private final long id;
    private final String name;
    private final String description;

    public Badge(long id, String name, String description) {
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

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Badge badge = (Badge) o;
        return id == badge.id &&
                name.equals(badge.name) &&
                description.equals(badge.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
