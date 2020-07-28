package com.company.userservice.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.company.userservice.controller.vm.UserVM;
import com.company.userservice.entity.Authority;
import com.company.userservice.entity.User;
import com.company.userservice.repository.UserRepository;
import com.company.userservice.security.constants.AuthoritiesConstants;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * Service class for managing users.
 */
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Regiser a new User.
     * 
     * @param userVm A user registration information.
     * @return The registered user.
     */
    public User registerNewUser(UserVM userVM) {
        // check email must not already exist.
        User newUser = new User();
        newUser.setUsername(userVM.getUsername());
        String encodedPassword = passwordEncoder.encode(userVM.getPassword());
        newUser.setPassword(encodedPassword);

        Set<Authority> authorities = new HashSet<>();
        Authority auth = new Authority();
        auth.setName(AuthoritiesConstants.USER);
        authorities.add(auth);
        newUser.setAuthorities(authorities);
        // save user
        userRepository.save(newUser);
        log.debug("Created a new User: {}", newUser);
        return newUser;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Long getCurrentlyAuthenticatedUserId(String username) {
        return userRepository.findOneByUsername(username).get().getId();
    }
}