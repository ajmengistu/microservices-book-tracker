package com.company.zuulgateway.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Web App Security Configuration.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JWTFilter jwtFilter;

    public SecurityConfiguration(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .cors().disable()
            .csrf().disable();
        http
        // Tell Spring Security to use your OWN custom UsernamePasswordAuthenticationFilter,
        // to determine if the current client/user making the request is authenticated,
        // by populating the SpringContextHolder object with an Authentication object.
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/user/api/register").permitAll()
            .antMatchers("/user/api/public").permitAll()
            .antMatchers("/user/api/authenticate").permitAll()
            .antMatchers("/user/api/users").permitAll()
            .antMatchers("/book/api/all").permitAll()
            .antMatchers("/user/**").authenticated()
            .antMatchers("/book/**").authenticated()
            .antMatchers("/**").authenticated();
            // Whether the current request is being made by an authenticated user
            // is determined by the jwtFilter via the SpringContextHolder object.
    }
}
