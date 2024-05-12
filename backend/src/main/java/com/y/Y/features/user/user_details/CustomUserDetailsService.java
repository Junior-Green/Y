package com.y.Y.features.user.user_details;

import com.y.Y.features.session.Session;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import java.util.UUID;

public interface CustomUserDetailsService extends UserDetailsService {

    CustomUserDetails loadUserBySessionId(UUID sessionId) throws SessionAuthenticationException;

    @Override
    CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
