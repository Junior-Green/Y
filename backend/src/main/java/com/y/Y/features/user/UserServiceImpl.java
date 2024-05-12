package com.y.Y.features.user;
import com.y.Y.error.custom_exceptions.DuplicateDataException;
import com.y.Y.features.auth.AuthService;
import com.y.Y.features.session.Session;
import com.y.Y.features.user.user_details.CustomUserDetails;
import com.y.Y.features.user.user_details.CustomUserDetailsService;
import org.springframework.http.HttpStatus;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthService authService;
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthService authService, CustomUserDetailsService customUserDetailsService) {
        this.userRepository = userRepository;
        this.authService = authService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User addNewUser(User user, String password) {
        Optional<User> duplicateEmail = userRepository.findUserByEmail(user.getEmail());
        if(duplicateEmail.isPresent()) throw new DuplicateDataException("email: " + user.getEmail() + " already exists", HttpStatus.CONFLICT, DuplicateDataException.DataType.USER_EMAIL);

        Optional<User> duplicatePhoneNumber = userRepository.findUserByPhoneNumber(user.getPhoneNumber());
        if(duplicatePhoneNumber.isPresent()) throw new DuplicateDataException("phone number: " + user.getPhoneNumber() + " already exists", HttpStatus.CONFLICT, DuplicateDataException.DataType.USER_PHONE_NUMBER);

        Optional<User> duplicateUsername = userRepository.findUserByUsername(user.getUsername());
        if(duplicateUsername.isPresent()) throw new DuplicateDataException("username: @" + user.getUsername()+ " already exists", HttpStatus.CONFLICT, DuplicateDataException.DataType.USERNAME);

        user.setAccountCreation(LocalDate.now());
        User savedUser = userRepository.save((user));
        authService.createNewUserAuth(savedUser.getId(),password);
        return savedUser;
    }

    @Override
    public User getUserById(UUID id) {
        return userRepository.getReferenceById(id);
    }

    @Override
    public User getUserByUsername(String username) throws EntityNotFoundException {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        if (userOptional.isPresent()){
            return userOptional.get();
        }
        else {
            throw new EntityNotFoundException("No user found with username: @" + username);
        }
    }

    @Override
    public User getUserByPhoneNumber(String phoneNumber) throws EntityNotFoundException {
        Optional<User> userOptional = userRepository.findUserByPhoneNumber(phoneNumber);
        if (userOptional.isPresent()){
            return userOptional.get();
        }
        else {
            throw new EntityNotFoundException("No user found with phone number: " + phoneNumber);
        }
    }

    @Override
    public void deleteUser(UUID id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()){
            throw new EntityNotFoundException("User id: " + id + " does not exist.");
        }
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

    public User getUserByEmail(String email) throws EntityNotFoundException {
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        if (userOptional.isPresent()){
            return userOptional.get();
        }
        else {
            throw new EntityNotFoundException("No user found with email: " + email);
        }
    }

    @Override
    @Transactional
    public User getUserBySession(UUID sessionId) {
        CustomUserDetails userDetails = customUserDetailsService.loadUserBySessionId(sessionId);
        return userRepository.getReferenceById(userDetails.getId());
    }
}
