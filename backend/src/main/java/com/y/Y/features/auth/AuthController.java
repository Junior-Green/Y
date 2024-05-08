package com.y.Y.features.auth;

import com.y.Y.features.auth.controller_requests.LoginRequest;
import com.y.Y.features.user.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {

    private final AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/{user_id}")
    @PutMapping
    public void updatePassword(
           @PathVariable("user_id") UUID id,
            @RequestBody() String newPassword
    ){
       authService.updatePassword(id, newPassword);
    }

    @GetMapping()
    @RequestMapping(path = "/{user_id}")
    public ResponseEntity<Auth> getUserAuth(
            @PathVariable("user_id") UUID id
    ){
        return ResponseEntity.ok(authService.getUserAuth(id));
    }

//    @PostMapping(path = "/login")
//    public ResponseEntity<HttpStatus> login(@RequestBody() LoginRequest credentials)
//    {
//
//    }

//    @PostMapping(path = "/logout")
//    public ResponseEntity<HttpStatus> login(@RequestBody() LoginRequest credentials)
//    {
//
//    }
}
