package com.y.Y.features.auth;

import java.util.UUID;

public interface AuthService {

    public void updatePassword(UUID user_id, String newPassword);

    public void createNewUserAuth(UUID user_id, String password);

    public Auth getUserAuth(UUID id);
}
