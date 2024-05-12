package com.y.Y.features.session;

import com.y.Y.features.user.User;
import com.y.Y.features.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class SessionServiceImpl implements SessionService{

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
    public void deleteSession(UUID sessionId) {
        sessionRepository.deleteById(sessionId);
    }

}
