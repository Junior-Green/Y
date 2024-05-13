package com.y.Y.features.user;

import com.y.Y.features.user.controller_requests.CreateNewUserRequest;
import com.y.Y.features.user.controller_requests.UpdateFollowersRequest;
import com.y.Y.features.user.user_details.CustomUserDetailsService;
import com.y.Y.features.user.user_details.CustomUserDetailsServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.y.Y.utils.UtilityService.extractSessionCookieFromRequest;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, CustomUserDetailsService customUserDetailsService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userService.getUsers());
    }

    @GetMapping(path = "/me")
    public ResponseEntity<User> getAuthenticatedUser(HttpServletRequest request){
        Cookie sessionCooke = extractSessionCookieFromRequest(request);
        User authenticatedUser = userService.getUserBySession(UUID.fromString(sessionCooke.getValue()));
        return ResponseEntity.ok(authenticatedUser);
    }

    @GetMapping(path = "/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userService.getUserByUsername(username));
    }

    @PostMapping(path = "/register")
    public ResponseEntity<User> createNewUser(@RequestBody CreateNewUserRequest req) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userService.addNewUser(req.getUser(), req.getPassword()));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable("id") UUID id,
            @RequestBody() User user
            ){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userService.updateUser(id, user));
    }

    @PutMapping(path = "/{id}/followers")
    public ResponseEntity<String> addFollowers(@PathVariable("id") UUID id, @RequestBody UpdateFollowersRequest request){

        return ResponseEntity.ok("Followers updated");
    }

    @DeleteMapping()
    public void deleteUser(@RequestParam("id") UUID id){
        userService.deleteUser(id);
    }

}
