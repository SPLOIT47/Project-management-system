package com.crm.view.handler;

import com.crm.cache.Cache;
import com.crm.controller.AuthenticationController;
import com.crm.model.dto.UserDTO;
import com.crm.model.entity.AppUser;
import com.crm.view.layout.LoginLayout;
import com.crm.view.layout.ProjectLayout;
import javafx.scene.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoginHandler {

    private final AuthenticationController authController;
    private final LoginLayout loginLayout;
    private final ProjectLayout projectLayout;
    private final Cache cache;

    @Autowired
    public LoginHandler(
            AuthenticationController authController,
            LoginLayout loginLayout,
            ProjectLayout projectLayout, Cache cache) {
        this.authController = authController;
        this.loginLayout = loginLayout;
        this.projectLayout = projectLayout;
        this.cache = cache;
    }

    public Scene handleLogin(String username, String password) {
        Optional<AppUser> optionalUser = this.authController.login(new UserDTO(username, password));
        if (optionalUser.isPresent()) {
            this.cache.add("userEntity", optionalUser.get());
            return this.projectLayout.createScene();
        }

        return this.loginLayout.createFailedLoginScene();
    }

    public Scene handleRegister(String username, String password) {
        this.authController.register(new UserDTO(username, password));
        return this.loginLayout.createScene();
    }

    public Scene showLoginMenu() {
        return this.loginLayout.createScene();
    }

    public Scene showRegisterMenu() {
        return this.loginLayout.createRegisterMenu();
    }
}

