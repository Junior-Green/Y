package com.y.Y.features.user.user_details;

import com.y.Y.features.session.Session;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import java.util.UUID;

public interface CustomUserDetailsService {
    CustomUserDetails loadUserBySessionId(UUID sessionId) throws SessionAuthenticationException;
}
