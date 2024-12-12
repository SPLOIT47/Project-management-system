package com.crm.presentation.layout;

import com.crm.presentation.handler.ActionHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@Component
public class LoginLayout {

    private final ObjectProvider<ActionHandler> actionControllerProvider;

    public LoginLayout(ObjectProvider<ActionHandler> actionControllerProvider) {
        this.actionControllerProvider = actionControllerProvider;
    }

    public Scene createScene() {
        return this.createLoginScene(null);
    }

    public Scene createFailedLoginScene() {
        return this.createLoginScene("Invalid credentials. Please try again.");
    }

    private Scene createLoginScene(String errorMessage) {
        GridPane loginLayout = new GridPane();
        loginLayout.setHgap(10);
        loginLayout.setVgap(10);
        loginLayout.setPadding(new Insets(40));

        if (errorMessage != null) {
            Label errorLabel = new Label(errorMessage);
            errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            loginLayout.add(errorLabel, 0, 0, 2, 1);
        }

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField();
        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");

        loginLayout.add(usernameLabel, 0, errorMessage != null ? 1 : 0);
        loginLayout.add(usernameField, 1, errorMessage != null ? 1 : 0);
        loginLayout.add(passwordLabel, 0, errorMessage != null ? 2 : 1);
        loginLayout.add(passwordField, 1, errorMessage != null ? 2 : 1);
        loginLayout.add(loginButton, 1, errorMessage != null ? 3 : 2);
        loginLayout.add(registerButton, 1, errorMessage != null ? 4 : 3);

        loginButton.setOnAction(actionEvent -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            ActionHandler actionHandler = this.actionControllerProvider.getIfAvailable();
            if (actionHandler != null) {
                actionHandler.handleLogin(username, password);
            }
        });

        registerButton.setOnAction(actionEvent -> {
            ActionHandler actionHandler = this.actionControllerProvider.getIfAvailable();
            if (actionHandler != null) {
                actionHandler.showRegisterMenu();
            }
        });

        return new Scene(loginLayout, 350, 250);
    }

    public Scene createRegisterMenu() {
        GridPane registerLayout = new GridPane();
        registerLayout.setHgap(10);
        registerLayout.setVgap(10);
        registerLayout.setPadding(new Insets(40));

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField();
        Label confirmPasswordLabel = new Label("Confirm Password:");
        TextField confirmPasswordField = new TextField();
        Button registerButton = new Button("Register");
        Button backButton = new Button("Back to Login");

        registerLayout.add(usernameLabel, 0, 0);
        registerLayout.add(usernameField, 1, 0);
        registerLayout.add(passwordLabel, 0, 1);
        registerLayout.add(passwordField, 1, 1);
        registerLayout.add(confirmPasswordLabel, 0, 2);
        registerLayout.add(confirmPasswordField, 1, 2);
        registerLayout.add(registerButton, 1, 3);
        registerLayout.add(backButton, 1, 4);

        registerButton.setOnAction(actionEvent -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if (!password.equals(confirmPassword)) {
                Label errorLabel = new Label("Passwords do not match.");
                errorLabel.setStyle("-fx-text-fill: red;");
                registerLayout.add(errorLabel, 1, 5);
            } else {
                ActionHandler actionHandler = this.actionControllerProvider.getIfAvailable();
                if (actionHandler != null) {
                    actionHandler.handleRegister(username, password);
                    actionHandler.showLoginMenu();
                }
            }
        });

        backButton.setOnAction(actionEvent -> {
            ActionHandler actionHandler = this.actionControllerProvider.getIfAvailable();
            if (actionHandler != null) {
                actionHandler.showLoginMenu();
            }
        });

        return new Scene(registerLayout, 350, 250);
    }
}
