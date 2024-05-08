package com.y.Y.filters;

import com.y.Y.features.auth.CustomAuthenticationManager;
import com.y.Y.features.auth.SessionAuthenticationToken;
import com.y.Y.features.session.Session;
import com.y.Y.features.session.SessionService;
import com.y.Y.features.session.SessionServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

public class CustomSessionAuthenticationFilter extends OncePerRequestFilter {
    final private SessionService sessionService;
    final private CustomAuthenticationManager authenticationManager;

    public CustomSessionAuthenticationFilter(SessionService sessionService, CustomAuthenticationManager authenticationManager) {
        this.sessionService = sessionService;
        this.authenticationManager = authenticationManager;
    }


    @Override
    protected void doFilterInternal( HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        Cookie sessionCookie = null;

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("session")) {
                sessionCookie = cookie;
                break;
            }
        }

        if (sessionCookie == null){
            throw new SessionAuthenticationException("Session cookie not found in request");
        }

        Session session = sessionService.getSessionById(UUID.fromString(sessionCookie.getValue()));
        Authentication authToken = new SessionAuthenticationToken(session);
        Authentication validatedAuthToken = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(validatedAuthToken);

        filterChain.doFilter(request, response);
    }
}
