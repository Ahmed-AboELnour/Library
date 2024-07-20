package com.library.service;

import com.library.entity.Role;
import com.library.entity.User;
import com.library.model.AuthRequest;
import com.library.model.RoleAssignmentRequest;
import com.library.repository.RoleRepository;
import com.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(AuthRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(request.getUserRoles());
        return userRepository.save(user);
    }


    public void addRoleToUser(RoleAssignmentRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        Role role = roleRepository.findByRoleName(request.getRoleName());
        if (role == null) {
            throw new IllegalArgumentException("Role not found");
        }
        user.getRoles().add(role);
        userRepository.save(user);
    }
}
