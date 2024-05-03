package com.y.Y.models.user;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.core.support.FragmentNotImplementedException;
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
    public User updateUser(UUID id, User newUser) {
            Optional<User> optionalUser = userRepository.findById(id);

            if (optionalUser.isEmpty()) {
                throw new EntityNotFoundException("User id: " + id + " does not exist.");
            }
            else{
                User foundUser = optionalUser.get();
                if(newUser.getFirstName() != null){
                    foundUser.setFirstName(newUser.getFirstName());
                }
                if(newUser.getLastName() != null){
                    foundUser.setLastName(newUser.getLastName());
                }
                if(newUser.getMiddleName() != null){
                    foundUser.setMiddleName(newUser.getMiddleName());
                }
                if(newUser.getBio() != null){
                    foundUser.setBio(newUser.getBio());
                }
                if(newUser.getDateOfBirth() != null){
                    foundUser.setDateOfBirth(newUser.getDateOfBirth());
                }
                if(newUser.getEmail() != null){
                    foundUser.setEmail(newUser.getEmail());
                }
                if(newUser.getGender() != null){
                    foundUser.setGender(newUser.getGender());
                }
                if(newUser.getUsername() != null){
                    foundUser.setUsername(newUser.getUsername());
                }
                if(newUser.getPhoneNumber() != null){
                    foundUser.setPhoneNumber(newUser.getPhoneNumber());
                }

                return userRepository.save(foundUser);
            }
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
