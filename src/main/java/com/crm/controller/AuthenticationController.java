package com.crm.controller;

import com.crm.model.dto.UserDTO;
import com.crm.model.entity.AppUser;
import com.crm.service.AuthenticationService;
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

    public AppUser register(UserDTO userDTO) {
        return this.service.registerUser(userDTO);
    }

    public Optional<AppUser> login(UserDTO userDTO) {
        return this.service.AuthenticateUser(userDTO);
    }

    public boolean tryGetAccess(AppUser appUser) {
        return true;
    }
}
