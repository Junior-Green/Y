package com.y.Y.features.user.user_details;

import com.y.Y.features.session.Session;
import com.y.Y.features.session.SessionRepository;
import com.y.Y.features.user.User;
import com.y.Y.features.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    @Autowired
    public CustomUserDetailsServiceImpl(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }


    @Override
    @Transactional
    public CustomUserDetails loadUserBySessionId(UUID sessionId) throws SessionAuthenticationException {
        Optional<Session> optionalSession = sessionRepository.findSessionById(sessionId);
        if(optionalSession.isEmpty()){
            throw new SessionAuthenticationException("Session " + sessionId + " has expired or does not exist");
        }

        Session session = optionalSession.get();
        return session.getUser();
    }

    @Override
    @Transactional
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        if(userOptional.isPresent()){
            return userOptional.get();
        }
        userOptional = userRepository.findUserByEmail(username);
        if(userOptional.isPresent()){
            return userOptional.get();
        }
        userOptional = userRepository.findUserByPhoneNumber(username);
        if(userOptional.isPresent()){
            return userOptional.get();
        }

        throw new UsernameNotFoundException(username + " does not exist");
    }
}
