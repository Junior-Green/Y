package com.y.Y.features.user.user_profile;

import com.y.Y.features.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public final class UserProfileImpl implements UserProfile {
    private User user;

    public UserProfileImpl(User user) {
        this.user = user;
    }

    public UserProfileImpl() {
    }

    @Override
    public UUID getId() {
        return user.getId();
    }

    @Override
    public String getLocation() {
        return user.getLocation();
    }

    @Override
    public LocalDate getBirthday() {
        return user.getBirthday();
    }

    @Override
    public String getDisplayName() {
        return user.getDisplayName();
    }

    @Override
    public LocalDateTime getAccountCreation() {
        return user.getAccountCreation();
    }

    @Override
    public String getBio() {
        return user.getBio();
    }

    @Override
    public String getWebsiteUrl() {
        return user.getWebsiteUrl();
    }

    @Override
    public Set<UUID> getFollowersIds() {
        return user.getFollowersIds();
    }

    @Override
    public Set<UUID> getFollowingIds() {
        return user.getFollowingIds();
    }

    @Override
    public boolean isVerified() {
        return user.isVerified();
    }
}
