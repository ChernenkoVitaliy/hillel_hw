package com.ra.course.stackoverflow.dto;

import java.time.LocalDateTime;

public class BountyDto {

    private Long id;
    private int reputation;
    private LocalDateTime expire;

    public BountyDto(Long id) {
        this.id = id;
    }

    public BountyDto(Long id, int reputation, LocalDateTime expire) {
        this.id = id;
        this.reputation = reputation;
        this.expire = expire;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public LocalDateTime getExpire() {
        return expire;
    }

    public void setExpire(LocalDateTime expire) {
        this.expire = expire;
    }
}
