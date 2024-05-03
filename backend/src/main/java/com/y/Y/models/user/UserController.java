package com.y.Y.models.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping
    public ResponseEntity<List<User>> getUsers() {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userService.getUsers());
    }

    @GetMapping(path = "/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userService.getUserByUsername(username));
    }

    @PostMapping
    public ResponseEntity<User> addNewUser(@RequestBody User user) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userService.addNewUser(user));
    }

    @PutMapping
    public ResponseEntity<User> updateUser(
            @RequestParam("id") UUID id,
            @RequestBody() User user
            ){
        logger.debug(user.toString());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userService.updateUser(id, user));
    }

    @DeleteMapping()
    public void deleteUser(@RequestParam("id") UUID id){
        userService.deleteUser(id);
    }

}
