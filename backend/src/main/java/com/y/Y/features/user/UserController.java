package com.y.Y.features.user;

import com.y.Y.features.like.Like;
import com.y.Y.features.like.LikeService;
import com.y.Y.features.user.controller_requests.CreateNewUserRequest;
import com.y.Y.features.user.user_details.CustomUserDetailsService;
import com.y.Y.features.user.user_profile.UserProfile;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
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

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userService.getUsers());
    }

    @GetMapping(path = "/me")
    public ResponseEntity<User> getAuthenticatedUser(HttpServletRequest request){
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();

        return ResponseEntity.ok(userService.getUserById((UUID) authenticatedUser.getPrincipal()));
    }

    @GetMapping(path = "/{username}")
    public ResponseEntity<UserProfile> getUserProfileByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getUserByUsername(username));
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
    public ResponseEntity<String> followUser(@PathVariable("id") UUID id){
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        userService.followUsers((UUID) authenticatedUser.getPrincipal(), Collections.singleton(id));
        return ResponseEntity.ok("User: " + id + " followed.");
    }

    @PostMapping(path = "/block/{id}")
    public ResponseEntity<String> blockUser(@PathVariable("id") UUID id){
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        userService.blockUsers((UUID) authenticatedUser.getPrincipal(), Collections.singleton(id));
        return  ResponseEntity.ok("User: " + id + " blocked.");
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable("id") UUID id,
            @RequestBody() User user
            ){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userService.updateUser(id, user));
    }

    @DeleteMapping(path = "/unfollow/{id}")
    public ResponseEntity<String> unfollowUser(@PathVariable("id") UUID id){
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        userService.unfollowUsers((UUID) authenticatedUser.getPrincipal(), Collections.singleton(id));
        return ResponseEntity.ok("User: " + id + " unfollowed.");
    }

    @DeleteMapping(path = "/unblock/{id}")
    public ResponseEntity<String> unblockUser(@PathVariable("id") UUID id){
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        userService.unblockUsers((UUID) authenticatedUser.getPrincipal(), Collections.singleton(id));
        return  ResponseEntity.ok("User: " + id + " unblocked.");
    }

    @DeleteMapping()
    public void deleteUser(@RequestParam("id") UUID id){
        userService.deleteUser(id);
    }

}
