package com.laconics.school_services_be.controller;

import com.laconics.school_services_be.auth.jwt.JwtService;
import com.laconics.school_services_be.dto.AuthRequest;
import com.laconics.school_services_be.dto.UserRequest;
import com.laconics.school_services_be.exception.BusinessException;
import com.laconics.school_services_be.model.User;
import com.laconics.school_services_be.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authManager;


    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserRequest request) throws BusinessException {
        return new ResponseEntity<>(userService.crateUser(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public String authAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request");
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<User>> findAllUsers(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
