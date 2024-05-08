package com.y.Y.features.auth.controller_requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.y.Y.features.user.User;

public final class LoginRequest {

    @JsonProperty("username")
    private final User username;
    private final String password;

    public LoginRequest(User username, String password) {
        this.username = username;
        this.password = password;
    }

    public User getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
