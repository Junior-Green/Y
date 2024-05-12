package com.y.Y.features.auth;

import com.y.Y.error.custom_exceptions.AuthenticationTokenException;
import com.y.Y.features.auth.tokens.SessionAuthenticationToken;
import com.y.Y.features.user.user_details.CustomUserDetails;
import com.y.Y.features.user.user_details.CustomUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.UUID;

public class CustomAuthenticationManager implements AuthenticationManager {

    private CustomUserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    public CustomAuthenticationManager() {}

    @Override
    public Authentication authenticate(Authentication authentication) {
        if(authentication.getClass().equals(SessionAuthenticationToken.class)){
            return authenticateSessionAuthenticationToken((SessionAuthenticationToken) authentication);
        } else if (authentication.getClass().equals(UsernamePasswordAuthenticationToken.class)) {
            return authenticateUsernamePasswordToken((UsernamePasswordAuthenticationToken) authentication);
        }
        throw new AuthenticationTokenException("Invalid authentication token", HttpStatus.UNAUTHORIZED);
    }

    public void setUserDetailsService(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    private Authentication authenticateSessionAuthenticationToken(SessionAuthenticationToken authentication){
        UUID sessionId = (UUID) authentication.getCredentials();

        CustomUserDetails userDetails = userDetailsService.loadUserBySessionId(sessionId);

        return new UsernamePasswordAuthenticationToken(userDetails.getId(), userDetails.getPassword(), new ArrayList<>());
    }

    private Authentication authenticateUsernamePasswordToken(UsernamePasswordAuthenticationToken authentication){
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        CustomUserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails.getId(), userDetails.getPassword(), new ArrayList<>());
    }
}
