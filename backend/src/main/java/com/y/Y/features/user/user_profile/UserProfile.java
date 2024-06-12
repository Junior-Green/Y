package com.y.Y.features.user.user_profile;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public interface UserProfile {

    public UUID getId();

    public String getUsername();

    public String getLocation();

    public LocalDate getBirthday();

    public String getDisplayName();

    public LocalDateTime getAccountCreation();

    public String getBio();

    public String getWebsiteUrl();

    public Set<UUID> getFollowersIds();

    public Set<UUID> getFollowingIds();

    public boolean isVerified();

}
