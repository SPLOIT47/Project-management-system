package com.crm.presentation.handler;

import com.crm.application.controller.AuthenticationController;
import com.crm.application.dto.UserDTO;
import com.crm.application.session.SessionManager;
import com.crm.domain.entity.User;
import com.crm.presentation.layout.LoginLayout;
import com.crm.presentation.layout.ProjectLayout;
import javafx.scene.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoginHandler {

    private final AuthenticationController authController;
    private final LoginLayout loginLayout;
    private final ProjectLayout projectLayout;
    private final SessionManager sessionManager;

    @Autowired
    public LoginHandler(
            AuthenticationController authController,
            LoginLayout loginLayout,
            ProjectLayout projectLayout,
            SessionManager sessionManager) {
        this.authController = authController;
        this.loginLayout = loginLayout;
        this.projectLayout = projectLayout;
        this.sessionManager = sessionManager;
    }

    public Scene handleLogin(String username, String password) {
        Optional<User> optionalUser = this.authController.login(new UserDTO(username, password));
        if (optionalUser.isPresent()) {
            this.sessionManager.setCurrentUser(optionalUser.get());
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

