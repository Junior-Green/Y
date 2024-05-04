package com.y.Y.models.auth;

import com.y.Y.models.user.User;
import com.y.Y.models.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService{

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
        String newSalt = BCrypt.gensalt();
        String newHashedPassword = BCrypt.hashpw(password, newSalt);

        User user = userRepository.getReferenceById(user_id);

        Auth newAuth = new Auth(user, newHashedPassword, newSalt);
        authRepository.save(newAuth);
    }
}
