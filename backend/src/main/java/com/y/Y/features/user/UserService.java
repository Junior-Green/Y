package com.y.Y.features.user;

import java.util.List;
import java.util.Set;
import java.util.UUID;


public interface UserService {

    List<User> getUsers();

    User addNewUser(User user, String password);

    User getUserById(UUID id);

    User getUserByUsername(String username);

    User getUserByEmail(String username);

    User getUserBySession(UUID sessionId);

    User getUserByPhoneNumber(String phoneNumber);

    void deleteUser(UUID id);

    void followUsers(UUID follower, Set<UUID> usersFollowed);

    void unfollowUsers(UUID unfollower, Set<UUID> usersUnfollowed);

    User updateUser(UUID id, User user);
}
