package com.crm.controller;

import com.crm.model.entity.user.AppUser;
import com.crm.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AuthenticationController {
    private final AuthenticationService service;

    @Autowired
    public AuthenticationController(
            AuthenticationService authenticationService) {
        this.service = authenticationService;
    }

    public void registerUser(AppUser appUser) {
        this.service.registerUser(appUser);
    }

    public boolean tryGetAccess(AppUser appUser) {
        return true;
    }
}
