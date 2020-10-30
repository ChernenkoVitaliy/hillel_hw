package com.ra.course.stackoverflow.entity;

import java.util.Objects;

public class Tag {

    private Long id;
    private final String name;
    private final String description;
    private int dailyAskedFrequency;
    private int weeklyAskedFrequency;
    private final Member author;

    public Tag(Long id, String name, String description, Member author) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getDailyAskedFrequency() {
        return dailyAskedFrequency;
    }

    public int getWeeklyAskedFrequency() {
        return weeklyAskedFrequency;
    }

    public Long getId() {
        return id;
    }

    public Member getAuthor() {
        return author;
    }

    public void setDailyAskedFrequency(int dailyAskedFrequency) {
        this.dailyAskedFrequency = dailyAskedFrequency;
    }

    public void setWeeklyAskedFrequency(int weeklyAskedFrequency) {
        this.weeklyAskedFrequency = weeklyAskedFrequency;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return dailyAskedFrequency == tag.dailyAskedFrequency &&
                weeklyAskedFrequency == tag.weeklyAskedFrequency &&
                id.equals(tag.id) &&
                name.equals(tag.name) &&
                description.equals(tag.description) &&
                author.equals(tag.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, dailyAskedFrequency, weeklyAskedFrequency, author);
    }
}
