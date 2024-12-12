package com.crm.presentation.handler;

import com.crm.application.controller.ProjectController;
import com.crm.application.dto.ProjectDTO;
import com.crm.application.dto.UserDTO;
import com.crm.application.session.SessionManager;
import com.crm.presentation.layout.ProjectLayout;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectHandler {
    private final ProjectController projectController;
    private final ProjectLayout projectLayout;
    private final SessionManager sessionManager;

    @Autowired
    public ProjectHandler(
            ProjectController projectController,
            ProjectLayout projectLayout,
            SessionManager sessionManager) {
        this.projectController = projectController;
        this.projectLayout = projectLayout;
        this.sessionManager = sessionManager;
    }

    public Scene getMainScene() {
        return this.projectLayout.createScene();
    }

    public Stream<ProjectDTO> getUserProjectNames(String username) {
        return this.projectController.getProjectNamesByUsername(username);
    }

    public Stage getProjectCreatingStage() {
        return this.projectLayout.createAdditionProjectDialog();
    }

    public void addProject(String name, String description) {
        String username = this.sessionManager.getCurrentUserName();
        this.projectController.createProject(username, name, description);
    }

    public Stage getProgectEditingStage(ProjectDTO projectDTO) {
        return this.projectLayout.createEditProjectDialog(projectDTO);
    }

    public Scene updateProject(ProjectDTO projectDTO) {
        this.projectController.updateProject(projectDTO);
        return this.getMainScene();
    }

    public Stream<UserDTO> getProjectUsers(String projectName) {
        return this.projectController.getUsersByProjectName(projectName);
    }

    public void getAddUserScene(ProjectDTO projectDTO) {
        this.projectLayout.showAddUserDialog(projectDTO, new ListView<>());
    }
}
