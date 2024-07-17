package com.y.Y.features.session;

import jakarta.servlet.http.Cookie;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import java.util.UUID;

public interface SessionService {

    public Session getSessionById(UUID sessionId) throws SessionAuthenticationException;

    public UUID createNewSession(UUID userId);

    public Cookie createNewSessionCookie(UUID sessionId);

    public Cookie createExpiredSessionCookie();

    public void deleteSession(UUID sessionId);
}
