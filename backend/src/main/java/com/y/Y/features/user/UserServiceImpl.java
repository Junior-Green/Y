package com.y.Y.features.user;
import com.y.Y.error.custom_exceptions.BadRequestException;
import com.y.Y.error.custom_exceptions.DuplicateDataException;
import com.y.Y.features.auth.AuthService;
import com.y.Y.features.post.Post;
import com.y.Y.features.post.PostService;
import org.springframework.http.HttpStatus;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostService postService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthService authService, PostService postService) {
        this.userRepository = userRepository;
        this.authService = authService;
        this.postService = postService;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User addNewUser(User user, String password) {
        if(user.getEmail() == null && user.getPhoneNumber() == null){
            throw new BadRequestException("An email or phone number must be provided to register an account", HttpStatus.BAD_REQUEST);
        }
        Optional<User> duplicateEmail = userRepository.findUserByEmail(user.getEmail());
        if(duplicateEmail.isPresent()) throw new DuplicateDataException("email: " + user.getEmail() + " already exists", HttpStatus.CONFLICT, DuplicateDataException.DataType.USER_EMAIL);

        Optional<User> duplicatePhoneNumber = userRepository.findUserByPhoneNumber(user.getPhoneNumber());
        if(duplicatePhoneNumber.isPresent()) throw new DuplicateDataException("phone number: " + user.getPhoneNumber() + " already exists", HttpStatus.CONFLICT, DuplicateDataException.DataType.USER_PHONE_NUMBER);

        Optional<User> duplicateUsername = userRepository.findUserByUsername(user.getUsername());
        if(duplicateUsername.isPresent()) throw new DuplicateDataException("username: @" + user.getUsername()+ " already exists", HttpStatus.CONFLICT, DuplicateDataException.DataType.USERNAME);

        user.setAccountCreation(LocalDateTime.now());
        User savedUser = userRepository.save((user));
        authService.createNewUserAuth(savedUser.getId(),password);
        return savedUser;
    }

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow();
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
    public void followUsers(UUID follower, Set<UUID> usersFollowed) {
        Optional<User> followerOptional = userRepository.findById(follower);

        if(followerOptional.isEmpty()){
            throw new EntityNotFoundException("User: " + follower + " does not exist");
        }

        User followerFetched = followerOptional.get();
        Set<User> usersFollowedFetched = new HashSet<>(usersFollowed.stream().map(id -> userRepository.findById(id).orElseThrow()).toList());
        for (User userFollowed : usersFollowedFetched){
            followerFetched.followUser(userFollowed);
            userFollowed.addFollower(followerFetched);
        }

        userRepository.save(followerFetched);
        userRepository.saveAll(usersFollowedFetched);
    }


    @Override
    @Transactional
    public void unfollowUsers(UUID unfollower, Set<UUID> usersUnfollowed) {
        Optional<User> unfollowerOptional = userRepository.findById(unfollower);

        if(unfollowerOptional.isEmpty()){
            throw new EntityNotFoundException("User: " + unfollower + " does not exist");
        }
        User unfollowerFetched = unfollowerOptional.get();

        Set<User> usersUnfollowedFetched = new HashSet<>(usersUnfollowed.stream().map(id -> userRepository.findById(id).orElseThrow()).toList());
        for (User userUnfollowed : usersUnfollowedFetched){
            if(userUnfollowed.getId() == unfollower){
                throw new BadRequestException("Cannot unfollow yourself" ,HttpStatus.BAD_REQUEST);
            }
            unfollowerFetched.unfollowUser(userUnfollowed);
            userUnfollowed.removeFollower(unfollowerFetched);
        }

        userRepository.save(unfollowerFetched);
        userRepository.saveAll(usersUnfollowedFetched);
    }

    @Override
    @Transactional
    public void blockUsers(UUID blockerId, Set<UUID> blockedUsers) {
        User blocker = userRepository.getReferenceById(blockerId);

        Set<User> blockedUsersFetched = new HashSet<>(blockedUsers.stream().map(userRepository::getReferenceById).toList());

        for (User blockedUser : blockedUsersFetched){
            blocker.blockUser(blockedUser);
        }

        userRepository.save(blocker);
    }

    @Override
    public void unblockUsers(UUID unblockerId, Set<UUID> unblockedUsers) {
        User unblocker = userRepository.getReferenceById(unblockerId);

        Set<User> unblockedUsersFetched = new HashSet<>(unblockedUsers.stream().map(userRepository::getReferenceById).toList());

        for (User unblockedUser : unblockedUsersFetched){
            unblocker.unblockUser(unblockedUser);
        }

        userRepository.save(unblocker);
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
                if(newUser.getBirthday() != null){
                    foundUser.setBirthday(newUser.getBirthday());
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

                if(newUser.getDisplayName() != null){
                    foundUser.setDisplayName(newUser.getDisplayName());
                }

                if(newUser.getWebsiteUrl() != null){
                    foundUser.setWebsiteUrl(newUser.getWebsiteUrl());
                }

                if(newUser.getLocation() != null){
                    foundUser.setLocation(newUser.getLocation());
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
    public void bookmarkPost(UUID bookmarker, UUID postId) {
        User user = userRepository.findById(bookmarker).orElseThrow();
        Post post = postService.getPostById(postId);

        user.bookmarkPost(post);
        userRepository.save(user);
    }

    @Override
    public void unBookmarkPost(UUID bookmarker, UUID postId) {
        User user = userRepository.findById(bookmarker).orElseThrow();
        Post post = postService.getPostById(postId);

        user.unBookmarkPost(post);
        userRepository.save(user);
    }
}
