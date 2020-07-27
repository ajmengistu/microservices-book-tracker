package com.company.userservice.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import com.company.userservice.repository.AuthorityRepository;
import com.company.userservice.repository.UserRepository;
import com.company.userservice.security.constants.AuthoritiesConstants;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;

    public CommandLineRunnerImpl(AuthorityRepository authorityRepository, UserRepository userRepository) {
        this.authorityRepository = authorityRepository;
        this.userRepository = userRepository;
    }

    private List<User> createUsers() {
        Set<Authority> authorities = new HashSet<>();
        List<User> users = new ArrayList<>();
        Authority auth = new Authority();
        auth.setName(AuthoritiesConstants.USER);
        authorities.add(auth);

        User user = new User();
        user.setUsername("hello@gmail.com");
        user.setPassword("hello");
        user.setAuthorities(authorities);
        users.add(user);

        return users;
    }

    @Override
    public void run(String... args) throws Exception {
        Stream.of(AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER, AuthoritiesConstants.ANONYMOUS)
                .forEach(auth -> {
                    Authority a = new Authority();
                    a.setName(auth);
                    authorityRepository.save(a);
                });
        createUsers().stream().forEach(user -> userRepository.save(user));
        ;
    }
}