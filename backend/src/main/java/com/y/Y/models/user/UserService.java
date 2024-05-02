package com.y.Y.models.user;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


public interface UserService {

    List<User> getUsers();

    User addNewUser(User user);

    User getUserById(UUID id);

    User getUserByUsername(String username);

    void deleteUser(UUID id);

    User updateUser(UUID id, String firstName, String middleName, String lastName, String email, LocalDate dateOfBirth, String phoneNumber, String gender, String bio);
}
