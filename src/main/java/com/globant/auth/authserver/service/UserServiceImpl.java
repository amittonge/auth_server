package com.globant.auth.authserver.service;

import com.globant.auth.authserver.Entity.User;
import com.globant.auth.authserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> loadUserByUsername(String username) {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            return Optional.empty();
        }
        return Optional.of(user);
    }

    @Override
    public String registerUser(User user) {
        User save = userRepository.save(user);
        return save.getUserName();
    }
}
