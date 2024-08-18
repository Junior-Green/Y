package com.y.Y.features.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public final class UserDto {
    private final User user;
    private UserSummaryViewDto summary;

    public UserDto(User user, UserSummaryViewDto summary) {
        this.user = user;
        this.summary = summary;
    }

    public UserDto(User user) {
        this.user = user;
    }

    public UUID getId() {
        return user.getId();
    }

    public String getUsername() {
        return user.getUsername();
    }

    public String getLocation() {
        return user.getLocation();
    }
    
    public LocalDate getBirthday() {
        return user.getBirthday();
    }
    
    public String getDisplayName() {
        return user.getDisplayName();
    }

    public LocalDateTime getAccountCreation() {
        return user.getAccountCreation();
    }

    public String getBio() {
        return user.getBio();
    }

    public String getWebsiteUrl() {
        return user.getWebsiteUrl();
    }

    public Integer getBlockedCount() {return summary.getBLOCKED_COUNT();}

    public Integer getFollowerCount() {return summary.getFOLLOWER_COUNT();}

    public Integer getFollowingCount() {return summary.getFOLLOWING_COUNT();}

    public Integer getBookmarkCount() {return summary.getBOOKMARK_COUNT();}

    public boolean isVerified() {
        return user.isVerified();
    }

    @JsonIgnore
    public Set<UUID> getBookmarks() {return user.getBookmarkedPostIds();}

    @JsonIgnore
    public Set<UUID> getFollowersIds() {
        return user.getFollowersIds();
    }

    @JsonIgnore
    public Set<UUID> getFollowingIds() {
        return user.getFollowingIds();
    }
}
