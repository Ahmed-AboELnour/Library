package com.library.model;

import com.library.entity.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class AuthRequest {

    private String username;
    private String password;
    private Set<Role> userRoles;

}
