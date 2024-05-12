package com.y.Y.features.auth;

import com.y.Y.error.custom_exceptions.StringLengthExceededException;
import com.y.Y.features.user.User;
import com.y.Y.features.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService{

    static final int MAX_PASSWORD_LENGTH = 32;

    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(AuthRepository authRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void updatePassword(UUID user_id, String rawPassword) {
        if (rawPassword.length() > MAX_PASSWORD_LENGTH){
            throw new StringLengthExceededException("Password length exceeds 32 characters (" + rawPassword + ")", HttpStatus.BAD_REQUEST, rawPassword);
        }

        Optional<Auth> optionalAuth = authRepository.findAuthByUserId(user_id);

        if (optionalAuth.isEmpty()){
            throw new EntityNotFoundException("User id:" + user_id + " does not exist.");
        }
        else{
            Auth auth = optionalAuth.get();
            auth.setPassword(passwordEncoder.encode(rawPassword));
            authRepository.save(auth);
        }
    }

    @Override
    public void createNewUserAuth(UUID user_id, String rawPassword) {
        if (rawPassword.length() > MAX_PASSWORD_LENGTH){
            throw new StringLengthExceededException("Password length exceeds 32 characters (" + rawPassword + ")", HttpStatus.BAD_REQUEST, rawPassword);
        }

        User user = userRepository.getReferenceById(user_id);

        Auth newAuth = new Auth(user, passwordEncoder.encode(rawPassword));
        authRepository.save(newAuth);
    }

    @Override
    public Auth getUserAuth(UUID id) {
        Optional<Auth> optionalAuth = authRepository.findAuthByUserId(id);

        if (optionalAuth.isEmpty()){
            throw new EntityNotFoundException("User id:" + id + " does not exist.");
        }
        else{
            return optionalAuth.get();
        }
    }
}
