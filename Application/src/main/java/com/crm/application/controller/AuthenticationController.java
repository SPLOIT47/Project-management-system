package com.crm.application.controller;

import com.crm.application.dto.UserDTO;
import com.crm.application.service.AuthenticationService;
import com.crm.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class AuthenticationController {
    private final AuthenticationService service;

    @Autowired
    public AuthenticationController(
            AuthenticationService authenticationService) {
        this.service = authenticationService;
    }

    public User register(UserDTO userDTO) {
        return this.service.registerUser(userDTO);
    }

    public Optional<User> login(UserDTO userDTO) {
        return this.service.AuthenticateUser(userDTO);
    }

    public boolean tryGetAccess(User appUser) {
        return true;
    }
}
