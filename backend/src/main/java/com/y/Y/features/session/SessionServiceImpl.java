package com.y.Y.features.session;

import com.y.Y.features.user.User;
import com.y.Y.features.user.UserRepository;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class SessionServiceImpl implements SessionService {

    @Value("${production}")
    private boolean production;

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    public Session getSessionById(UUID sessionId) throws SessionAuthenticationException {
        Optional<Session> optionalSession = sessionRepository.findSessionById(sessionId);

        if (optionalSession.isEmpty()){
            throw new SessionAuthenticationException("session id: " + sessionId + " not found");
        }

        return optionalSession.get();
    }

    @Override
    public UUID createNewSession(UUID userId) {
        User user = userRepository.getReferenceById(userId);
        Session newSession = new Session(LocalDateTime.now().plusSeconds(Session.SESSION_EXPIRATION_SECONDS),user);
        Session savedSession = sessionRepository.save(newSession);
        return savedSession.getId();
    }

    @Override
    public Cookie createNewSessionCookie(UUID sessionId) {
        Cookie sessionCookie = new Cookie("session", sessionId.toString());
        sessionCookie.setHttpOnly(production);
        sessionCookie.setSecure(true);
        if (!production)
        {
            sessionCookie.setDomain("localhost");
        }
        sessionCookie.setAttribute("SameSite", production ? "strict" : "None");
        sessionCookie.setPath("/api");
        sessionCookie.setMaxAge(Session.SESSION_EXPIRATION_SECONDS);

        return sessionCookie;
    }

    @Override
    public Cookie createExpiredSessionCookie() {
        Cookie sessionCookie = new Cookie("session",null);
        sessionCookie.setHttpOnly(production);
        sessionCookie.setSecure(true);
        if (!production)
        {
            sessionCookie.setDomain("localhost");
        }
        sessionCookie.setAttribute("SameSite", production ? "strict" : "None");
        sessionCookie.setPath("/api");
        sessionCookie.setMaxAge(0);

        return sessionCookie;
    }

    @Override
    public void deleteSession(UUID sessionId) {
        sessionRepository.deleteById(sessionId);
    }

}
