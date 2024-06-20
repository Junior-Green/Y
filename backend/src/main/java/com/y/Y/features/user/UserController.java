package com.y.Y.features.user;

import com.y.Y.features.like.Like;
import com.y.Y.features.like.LikeService;
import com.y.Y.features.user.controller_requests.CreateNewUserRequest;
import com.y.Y.features.user.user_details.CustomUserDetailsService;
import com.y.Y.features.user.user_profile.UserProfile;
import com.y.Y.features.user.user_profile.UserProfileImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(originPatterns = "http://localhost:5173")
@RequestMapping(path = "/api/users")
public class UserController {

    private final UserService userService;
    private final LikeService likeService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, CustomUserDetailsService customUserDetailsService, LikeService likeService) {
        this.userService = userService;
        this.likeService = likeService;
    }

    @GetMapping(path = "/me")
    public ResponseEntity<User> getAuthenticatedUser(){
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();

        return ResponseEntity.ok(userService.getUserById((UUID) authenticatedUser.getPrincipal()));
    }

    @GetMapping
    public ResponseEntity<UserProfile> getUserProfile(
            @RequestParam(value = "id", required = false) UUID id,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phone", required = false) String phoneNumber,
            @RequestParam(value = "username", required = false) String username) {

        if (id != null){
            return ResponseEntity.ok().body(new UserProfileImpl(userService.getUserById(id)));
        }
        if(username != null){
            return ResponseEntity.ok(new UserProfileImpl(userService.getUserByUsername(username)));
        }
        if (email != null) {
            return ResponseEntity.ok().body(new UserProfileImpl(userService.getUserByEmail(email)));
        }
        if(phoneNumber != null){
            return ResponseEntity.ok().body(new UserProfileImpl(userService.getUserByPhoneNumber(phoneNumber)));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @GetMapping("/likes/{id}")
    public ResponseEntity<List<Like>> getAllUserLikes(@PathVariable("id") UUID id){
        return ResponseEntity.ok(likeService.getLikesByUserId(id));
    }

    @PostMapping(path = "/register")
    public ResponseEntity<User> createNewUser(@RequestBody CreateNewUserRequest req) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userService.addNewUser(req.getUser(), req.getPassword()));
    }

    @PostMapping(path = "/follow/{id}")
    public ResponseEntity<HttpStatus> followUser(@PathVariable("id") UUID id){
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        userService.followUsers((UUID) authenticatedUser.getPrincipal(), Collections.singleton(id));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping(path = "/block/{id}")
    public ResponseEntity<HttpStatus> blockUser(@PathVariable("id") UUID id){
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        userService.blockUsers((UUID) authenticatedUser.getPrincipal(), Collections.singleton(id));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping(path = "/me")
    public ResponseEntity<User> updateAuthenticatedUser(
            @RequestBody() User user
            ){
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userService.updateUser((UUID) authenticatedUser.getPrincipal(), user));
    }

    @DeleteMapping(path = "/unfollow/{id}")
    public ResponseEntity<HttpStatus> unfollowUser(@PathVariable("id") UUID id){
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        userService.unfollowUsers((UUID) authenticatedUser.getPrincipal(), Collections.singleton(id));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping(path = "/unblock/{id}")
    public ResponseEntity<HttpStatus> unblockUser(@PathVariable("id") UUID id){
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        userService.unblockUsers((UUID) authenticatedUser.getPrincipal(), Collections.singleton(id));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping(path = "/me")
    public void deleteUser(){
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        userService.deleteUser((UUID) authenticatedUser.getPrincipal());
    }

}
