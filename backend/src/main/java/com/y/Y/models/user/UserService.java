package com.y.Y.models.user;

import java.util.List;
import java.util.UUID;


public interface UserService {

    List<User> getUsers();

    User addNewUser(User user, String password);

    User getUserById(UUID id);

    User getUserByUsername(String username);

    void deleteUser(UUID id);

    User updateUser(UUID id, User user);
}
