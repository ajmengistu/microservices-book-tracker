package com.company.userservice.controller.vm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * A View Model object for storing a user's credentials.
 */
@NoArgsConstructor
@Setter
@Getter
@ToString
public class LoginVM {

    private String username;
    private String password;
}