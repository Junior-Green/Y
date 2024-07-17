package com.y.Y.features.auth;

import com.y.Y.error.custom_exceptions.MissingCookieException;
import com.y.Y.features.auth.controller_requests.LoginRequest;
import com.y.Y.features.session.SessionService;
import com.y.Y.features.session.Session;
import com.y.Y.features.user.UserController;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(originPatterns = "http://localhost:5173")
@RequestMapping(path = "/api/auth")
public class AuthController {

    private final AuthService authService;
    private final SessionService sessionService;
    private final AuthenticationManager authenticationManager;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public AuthController(AuthService authService, SessionService sessionService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.sessionService = sessionService;
        this.authenticationManager = authenticationManager;
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/{user_id}")
    public void updatePassword(
           @PathVariable("user_id") UUID id,
            @RequestBody() String rawPassword
    ){
       authService.updatePassword(id, rawPassword);
    }

    @GetMapping(path = "/{user_id}")
    public ResponseEntity<Auth> getUserAuth(
            @PathVariable("user_id") UUID id
    ){
        return ResponseEntity.ok(authService.getUserAuth(id));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<HttpStatus> login(@RequestBody() LoginRequest credentials, HttpServletResponse response)
    {
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        Authentication unAuthenticated = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticated = authenticationManager.authenticate(unAuthenticated);
        SecurityContextHolder.getContext().setAuthentication(authenticated);

        UUID newSessionId = sessionService.createNewSession((UUID) authenticated.getPrincipal());
        Cookie sessionCookie = sessionService.createNewSessionCookie(newSessionId);

        response.addCookie(sessionCookie);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<HttpStatus> logout(HttpServletRequest request, HttpServletResponse response)
    {
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies){
            if(cookie.getName().equals("session")){
                sessionService.deleteSession(UUID.fromString(cookie.getValue()));
                Cookie sessionCookie = sessionService.createExpiredSessionCookie();
                response.addCookie(sessionCookie);

                return ResponseEntity.ok(HttpStatus.OK);
            }
        }

        throw new MissingCookieException(HttpStatus.BAD_REQUEST,"session");
    }
}
