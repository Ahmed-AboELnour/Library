package com.library.service;

import com.library.entity.Role;
import com.library.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;


    public void addRole(String roleName) {
        List<Role> existingRoles = roleRepository.findAll();

        boolean roleExists = existingRoles.stream()
                .anyMatch(role -> role.getRoleName().equalsIgnoreCase(roleName));

        if (!roleExists) {
            Role newRole = new Role();
            newRole.setRoleName(roleName);
            roleRepository.save(newRole);
        }
    }
}
