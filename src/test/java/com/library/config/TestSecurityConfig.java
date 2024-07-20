package com.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class TestSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            // Return a dummy UserDetails object for testing
            if ("testUser".equals(username)) {
                return User.withUsername(username)
                        .password("password")
                        .authorities("USER")
                        .build();
            } else {
                throw new UsernameNotFoundException("User not found");
            }
        };
    }
}