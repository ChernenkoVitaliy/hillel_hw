package com.ra.course.stackoverflow.entity;

import java.util.Objects;
import java.util.Set;

public class Member {

    protected final Account account;
    protected Set<Badge> badges;

    public Member(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public int getReputation() {
        return account.getReputation();
    }

    public String getEmail() {
        return account.getEmail();
    }

    public Set<Badge> getBadges() {
        return badges;
    }

    public void setBadges(Set<Badge> badges) {
        this.badges = badges;
    }

    public void addBadge(Badge badge) {
        badges.add(badge);
    }

    public void removeBadge(Badge badge) {
        badges.remove(badge);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(account, member.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account);
    }
}
