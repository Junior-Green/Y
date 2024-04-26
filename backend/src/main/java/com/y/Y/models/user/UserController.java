package com.y.Y.models.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    @GetMapping
    public List<User> getUsers() {
        return List.of(new User("quick","jr","","green", "jr@gmail.com", LocalDate.of(2002,3,20), LocalDate.now(), "647", "M", "This is my bio"));
    }
}
