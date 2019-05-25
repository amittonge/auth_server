package com.globant.auth.authserver.controller;

import com.globant.auth.authserver.Entity.User;
import com.globant.auth.authserver.error.ErrorResponse;
import com.globant.auth.authserver.error.UserNameAlreadyExist;
import com.globant.auth.authserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        if (isUserNameExists(user.getUserName())) {
            throw new UserNameAlreadyExist(String.format("%s is already exist, please try with different username", user.getUserName()));
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return userService.registerUser(user);
    }

    @ExceptionHandler({Exception.class})
    public ErrorResponse handleRegistrationException(
            Exception ex, WebRequest request) {
        return ErrorResponse.of(HttpStatus.BAD_REQUEST, ex.getMessage(), ex.toString());
    }

    @GetMapping("/exist/{userName}")
    public boolean isUserNameExists(@PathVariable("userName") String userName) {
        Optional<User> user = userService.loadUserByUsername(userName);
        return user.isPresent();
    }
}
