package com.y.Y.features.user.controller_requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class UpdateFollowersRequest {

    @JsonProperty(required = true, value = "user_ids")
    private Set<UUID> userIds;

    public UpdateFollowersRequest() {}

    public UpdateFollowersRequest(Set<UUID> userIds) {
        this.userIds = userIds;
    }

    public Set<UUID> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<UUID> userIds) {
        this.userIds = userIds;
    }
}
