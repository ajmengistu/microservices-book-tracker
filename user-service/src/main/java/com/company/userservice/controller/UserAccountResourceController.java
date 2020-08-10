package com.company.userservice.controller;

import java.util.List;

import javax.validation.Valid;

import com.company.userservice.controller.vm.UserVM;
import com.company.userservice.entity.User;
import com.company.userservice.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
@Slf4j
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
public class UserAccountResourceController {

    private final UserService userService;

    public UserAccountResourceController(UserService userService) {
        this.userService = userService;
    }

    /**
     * {@code POST /register} : register the new user.
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerAccount(@Valid @RequestBody UserVM userVM) {
        log.debug("Account registration requested by, {}", userVM);
        return userService.registerNewUser(userVM);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/public")
    public String getPublicResource() {
        return "This is a public resource! No Authentication needed!";
    }

    @GetMapping("/secure")
    public String getSecureResource() {
        return "This is a secure resource! Authentication needed!";
    }

    @GetMapping("/user-id")
    public ResponseEntity<String> getCurrentlyAuthenticatedUserId(Authentication authentication) {
        var userId = userService.getCurrentlyAuthenticatedUserId(authentication.getName());
        return new ResponseEntity<String>(userId.toString(), HttpStatus.OK);
    }
}
