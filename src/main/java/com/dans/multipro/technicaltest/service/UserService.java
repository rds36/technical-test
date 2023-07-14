package com.dans.multipro.technicaltest.service;

import com.dans.multipro.technicaltest.data.entity.User;
import com.dans.multipro.technicaltest.data.model.RegisterRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface UserService {
    User getUser(UUID id);
    User getUser(String username);
    User saveUser(RegisterRequest registerRequest);
}
