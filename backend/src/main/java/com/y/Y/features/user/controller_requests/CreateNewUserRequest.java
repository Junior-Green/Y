package com.y.Y.features.user.controller_requests;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.y.Y.features.user.User;

public class CreateNewUserRequest {

    @JsonProperty("user")
    private User user;
    private String password;

    public CreateNewUserRequest(){}

    public CreateNewUserRequest(User user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public User getUser() {
        return user;
    }
}
