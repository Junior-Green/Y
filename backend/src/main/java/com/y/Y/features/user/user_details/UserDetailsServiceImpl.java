package com.y.Y.features.user.user_details;

import com.y.Y.features.session.Session;
import com.y.Y.features.session.SessionService;
import com.y.Y.features.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements CustomUserDetailsService {

    private final UserService userService;
    private final SessionService sessionService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }


    @Override
    public CustomUserDetails loadUserBySessionId(UUID sessionId) throws SessionAuthenticationException {
        Session session = sessionService.getSessionById(sessionId);
        return session.getUser();

    }
}
