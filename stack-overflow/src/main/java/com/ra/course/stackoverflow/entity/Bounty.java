package com.ra.course.stackoverflow.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Bounty {

    private Long id;
    private int reputation;
    private LocalDateTime expire;

    public Bounty(Long id) {
        this.id = id;
    }

    public Bounty(Long id, int reputation, LocalDateTime expire) {
        this.id = id;
        this.reputation = reputation;
        this.expire = expire;
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

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bounty bounty = (Bounty) o;
        return reputation == bounty.reputation &&
                id.equals(bounty.id) &&
                Objects.equals(expire, bounty.expire);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reputation, expire);
    }
}
