package com.globant.auth.authserver.service;

import com.globant.auth.authserver.Entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> loadUserByUsername(String username);
    String registerUser(User user);
}
