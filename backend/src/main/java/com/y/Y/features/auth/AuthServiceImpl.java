package com.y.Y.features.auth;

import com.y.Y.error.custom_exceptions.StringLengthExceededException;
import com.y.Y.features.user.User;
import com.y.Y.features.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService{

    static final int MAX_PASSWORD_LENGTH = 32;

    private final AuthRepository authRepository;
    private final UserRepository userRepository;

    @Autowired
    public AuthServiceImpl(AuthRepository authRepository, UserRepository userRepository) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void updatePassword(UUID user_id, String newPassword) {
        if (newPassword.length() > MAX_PASSWORD_LENGTH){
            throw new StringLengthExceededException("Password length exceeds 32 characters (" + newPassword + ")", HttpStatus.BAD_REQUEST, newPassword);
        }

        Optional<Auth> optionalAuth = authRepository.findAuthByUserId(user_id);

        if (optionalAuth.isEmpty()){
            throw new EntityNotFoundException("User id:" + user_id + " does not exist.");
        }
        else{
            Auth auth = optionalAuth.get();
            String newSalt = BCrypt.gensalt();
            String newHashedPassword = BCrypt.hashpw(newPassword, newSalt);
            auth.setPassword(newHashedPassword);
            auth.setSalt(newSalt);
            authRepository.save(auth);
        }
    }

    @Override
    public void createNewUserAuth(UUID user_id, String password) {
        if (password.length() > MAX_PASSWORD_LENGTH){
            throw new StringLengthExceededException("Password length exceeds 32 characters (" + password + ")", HttpStatus.BAD_REQUEST, password);
        }

        String newSalt = BCrypt.gensalt();
        String newHashedPassword = BCrypt.hashpw(password, newSalt);

        User user = userRepository.getReferenceById(user_id);

        Auth newAuth = new Auth(user, newHashedPassword, newSalt);
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
