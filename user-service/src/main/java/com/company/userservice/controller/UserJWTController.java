package com.company.userservice.controller;

import javax.validation.Valid;

import com.company.userservice.controller.vm.LoginVM;
import com.company.userservice.security.jwt.JWTProvider;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
public class UserJWTController {
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final JWTProvider jWTProvider;

    private final AuthenticationManager authenticationManager;

    public UserJWTController(AuthenticationManager authenticationManager, JWTProvider jwtProvider) {
        this.jWTProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<JWTToken> authenticate(@Valid @RequestBody LoginVM loginVM) {
        // Allow AuthenticationManager to authenticate the user attempting to login.
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginVM.getUsername(), loginVM.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // If user is authenticated, ISSUE a JWT.
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jWTProvider.createToken(authentication);

        return new ResponseEntity<>(new JWTToken(jwt), HttpStatus.OK);
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    @AllArgsConstructor
    @Setter
    @Getter
    private static class JWTToken {
        private String idToken;
    }

}