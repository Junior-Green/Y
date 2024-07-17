package com.y.Y.features.auth;

import jakarta.servlet.http.Cookie;

import java.util.UUID;

public interface AuthService {

    public void updatePassword(UUID user_id, String rawPassword);

    public void createNewUserAuth(UUID user_id, String rawPassword);

    public Auth getUserAuth(UUID id);
}
