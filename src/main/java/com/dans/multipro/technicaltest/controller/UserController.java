package com.dans.multipro.technicaltest.controller;

import com.dans.multipro.technicaltest.data.entity.User;
import com.dans.multipro.technicaltest.data.model.RegisterRequest;
import com.dans.multipro.technicaltest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<String> findById(@PathVariable String id){
        User user = userService.getUser(UUID.fromString(id));
        return new ResponseEntity<>(user.getUsername(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@Valid @RequestBody RegisterRequest registerRequest){
        userService.saveUser(registerRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
