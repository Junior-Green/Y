package com.y.Y.models.user;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addNewUser(User user) {
        user.setAccountCreation(LocalDate.now());
        return userRepository.save((user));
    }

    @Override
    public User getUserById(UUID id) {
        return userRepository.getReferenceById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        if (userOptional.isPresent()){
            return userOptional.get();
        }
        else {
            throw new EntityNotFoundException("No user found with username: @" + username);
        }
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public User updateUser(UUID id, String firstName, String middleName, String lastName, String email, LocalDate dateOfBirth, String phoneNumber, String gender, String bio ) {

    }

    public User getUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        if (userOptional.isPresent()){
            return userOptional.get();
        }
        else {
            throw new EntityNotFoundException("No user found with email: " + email);
        }
    }
}
