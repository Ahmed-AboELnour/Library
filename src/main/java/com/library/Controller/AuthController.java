package com.library.Controller;


import com.library.entity.User;
import com.library.model.AuthRequest;
import com.library.model.RoleAssignmentRequest;
import com.library.service.RegistrationService;
import com.library.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/authenticate")
    public String createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return jwt;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody AuthRequest request) {
        User user = registrationService.registerUser(request);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


    @PostMapping("/addRoleToUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleAssignmentRequest request) {
        registrationService.addRoleToUser(request);
        return ResponseEntity.ok().build();
    }


}
