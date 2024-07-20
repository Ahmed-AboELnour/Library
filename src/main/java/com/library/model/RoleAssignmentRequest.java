package com.library.model;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleAssignmentRequest {

    private String username;
    private String roleName;

}
