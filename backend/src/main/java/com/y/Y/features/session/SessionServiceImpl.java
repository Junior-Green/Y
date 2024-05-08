package com.y.Y.features.session;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SessionServiceImpl implements SessionService{

    private final SessionRepository sessionRepository;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Session getSessionById(UUID sessionId) throws SessionAuthenticationException {
        Optional<Session> optionalSession = sessionRepository.findSessionById(sessionId);

        if (optionalSession.isEmpty()){
            throw new SessionAuthenticationException("session id: " + sessionId + " not found");
        }

        return optionalSession.get();
    }

}
