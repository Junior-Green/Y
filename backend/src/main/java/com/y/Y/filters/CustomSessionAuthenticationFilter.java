package com.y.Y.filters;

import com.y.Y.config.SecurityConfiguration;
import com.y.Y.error.custom_exceptions.MissingCookieException;
import com.y.Y.features.auth.tokens.SessionAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.y.Y.utils.UtilityService.extractSessionCookieFromRequest;

public class CustomSessionAuthenticationFilter extends OncePerRequestFilter {

    final private AuthenticationManager authenticationManager;

    public CustomSessionAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void doFilterInternal(@NotNull HttpServletRequest request,
                                 @NotNull HttpServletResponse response,
                                 @NotNull FilterChain filterChain) throws ServletException, IOException, MissingCookieException {
        Cookie sessionCookie = extractSessionCookieFromRequest(request);

        Authentication authToken = new SessionAuthenticationToken(UUID.fromString(sessionCookie.getValue()));
        Authentication validatedAuthToken = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(validatedAuthToken);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        final List<String> urls = Arrays.asList(SecurityConfiguration.PUBLIC_URLS);
        return urls.contains(request.getServletPath());
    }
}
