package com.crm.presentation.handler;

import com.crm.application.dto.ProjectDTO;
import com.crm.application.dto.UserDTO;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Lazy
@Component
public class ActionHandler {
    @Setter
    private Stage stage;
    private final LoginHandler loginHandler;
    private final ProjectHandler projectHandler;
    private final TaskHandler taskHandler;

    @Autowired
    public ActionHandler(
            LoginHandler loginHandler,
            ProjectHandler projectHandler,
            TaskHandler taskHandler) {
        this.loginHandler = loginHandler;
        this.projectHandler = projectHandler;
        this.taskHandler = taskHandler;
    }

    public void handleLogin(String username, String password) {
        this.stage.setScene(this.loginHandler.handleLogin(username, password));
    }

    public void showLoginMenu() {
        this.stage.setScene(this.loginHandler.showLoginMenu());
    }

    public void showRegisterMenu() {
        this.stage.setScene(this.loginHandler.showRegisterMenu());
    }

    public void showProjectCreatingMenu() {
        Stage dialog = this.projectHandler.getProjectCreatingStage();
        dialog.initOwner(this.stage);
        dialog.showAndWait();
    }

    public void showProjectEditingMenu(ProjectDTO projectDTO) {
        this.projectHandler.getProgectEditingStage(projectDTO).showAndWait();
    }

    public void handleRegister(String username, String password) {
        this.stage.setScene(this.loginHandler.handleRegister(username, password));
    }

    public void handleAddProject(String projectName, String description) {
        this.projectHandler.addProject(projectName, description);
        this.stage.setScene(this.projectHandler.getMainScene());
    }

    public Stream<ProjectDTO> getUserProjectDTO(String username) {
        return this.projectHandler.getUserProjectNames(username);
    }

    public void handleEditProject(ProjectDTO projectDTO) {
        this.stage.setScene(this.projectHandler.updateProject(projectDTO));
    }

    public Stream<UserDTO> getProjectUsers(String projectName) {
        return this.projectHandler.getProjectUsers(projectName);
    }

    public void showAddUserDialog(ProjectDTO projectDTO) {
        this.projectHandler.getAddUserScene(projectDTO);
    }
}
