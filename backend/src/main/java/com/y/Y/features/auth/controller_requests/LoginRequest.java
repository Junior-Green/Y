package com.y.Y.features.auth.controller_requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.y.Y.features.user.User;

public final class LoginRequest {

    private final String username;
    private final String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
