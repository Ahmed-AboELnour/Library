package com.library.service;

import com.library.entity.Role;
import com.library.entity.User;
import com.library.model.AuthRequest;
import com.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(AuthRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        // Add default role, e.g., ROLE_USER
        Role role = new Role();
        role.setName(Role.RoleName.ROLE_USER);
        //user.setAuthorities(Collections.singletonList(role));
        return userRepository.save(user);
    }
}
