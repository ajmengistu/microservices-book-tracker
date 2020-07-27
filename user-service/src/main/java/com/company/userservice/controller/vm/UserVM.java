package com.company.userservice.controller.vm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * A View Model for a User. Use case example: new user registration.
 */
@NoArgsConstructor
@Setter
@Getter
@ToString
public class UserVM {

    private String username;
    private String password;
}