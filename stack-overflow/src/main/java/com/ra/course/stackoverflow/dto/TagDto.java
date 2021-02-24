package com.ra.course.stackoverflow.dto;

import com.ra.course.stackoverflow.entity.Member;

public class TagDto {

    private Long id;
    private final String name;
    private final String description;
    private int dailyAskedFrequency;
    private int weeklyAskedFrequency;
    private final Member author;

    public TagDto(Long id, String name, String description, Member author) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setDailyAskedFrequency(int dailyAskedFrequency) {
        this.dailyAskedFrequency = dailyAskedFrequency;
    }

    public int getWeeklyAskedFrequency() {
        return weeklyAskedFrequency;
    }

    public void setWeeklyAskedFrequency(int weeklyAskedFrequency) {
        this.weeklyAskedFrequency = weeklyAskedFrequency;
    }

    public Member getAuthor() {
        return author;
    }
}
