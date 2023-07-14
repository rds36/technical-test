package com.dans.multipro.technicaltest.service;

import com.dans.multipro.technicaltest.data.entity.User;
import com.dans.multipro.technicaltest.data.model.RegisterRequest;
import com.dans.multipro.technicaltest.exception.EntityNotFoundException;
import com.dans.multipro.technicaltest.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User getUser(UUID id) {
        Optional<User> user = userRepository.findById(id);
        return parseUser(user);
    }

    @Override
    public User getUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return parseUser(user);
    }

    @Override
    public User saveUser(RegisterRequest registerRequest) {
        User user = registerRequestToUser(registerRequest);
        User result = userRepository.save(user);
        log.info("Registered new user with id: {}", result.getUid());
        return result;
    }

    private User registerRequestToUser(RegisterRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        return User.builder()
                .username(request.getUsername())
                .password(encodedPassword)
                .build();
    }

    static User parseUser(Optional<User> entity){
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(User.class);
    }
}
