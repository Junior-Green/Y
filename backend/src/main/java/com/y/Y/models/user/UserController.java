package com.y.Y.models.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class UserController {

    @RequestMapping(path = "hello")
    @GetMapping
    public String hello() {
        return "Hello World";
    }
}
