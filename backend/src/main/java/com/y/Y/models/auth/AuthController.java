package com.y.Y.models.auth;

import com.y.Y.models.user.User;
import com.y.Y.models.user.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @PutMapping
    public void updatePassword(
            @RequestParam("id") UUID id,
            @RequestBody() String newPassword
    ){
       authService.updatePassword(id, newPassword);
    }
}
