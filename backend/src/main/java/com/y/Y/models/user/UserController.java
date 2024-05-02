package com.y.Y.models.user;

import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleIllegalArgumentException(EntityNotFoundException e) {
        return new ResponseEntity<>("Illegal argument: " + e.getMessage(), HttpStatus.NOT_FOUND);
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
    public void updateUser(
            @RequestParam("id") UUID id,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "middleName", required = false) String middleName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "dateOfBirth", required = false) LocalDate dateOfBirth,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestBody(required = false) String bio
            ){
       // userService.updateUser(UUID.fromString("3"),"");
    }

    @DeleteMapping()
    public void deleteUser(@RequestParam("id") UUID id){
        userService.deleteUser(id);
    }

}
