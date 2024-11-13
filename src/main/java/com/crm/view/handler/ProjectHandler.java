package com.crm.view.handler;

import com.crm.cache.Cache;
import com.crm.controller.ProjectController;
import com.crm.model.dto.ProjectDTO;
import com.crm.model.entity.AppUser;
import com.crm.view.layout.ProjectLayout;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class ProjectHandler {
    private final ProjectController projectController;
    private final ProjectLayout projectLayout;
    private final Cache cache;

    @Autowired
    public ProjectHandler(ProjectController projectController, ProjectLayout projectLayout, Cache cache) {
        this.projectController = projectController;
        this.projectLayout = projectLayout;
        this.cache = cache;
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
        String username = this.cache
                .getValueAsType("userEntity", AppUser.class)
                .map(AppUser::getUsername)
                .orElse("");

        this.projectController.createProject(username, name, description);
    }

    public Stage getProgectEditingStage(ProjectDTO projectDTO) {
        return this.projectLayout.createEditProjectDialog(projectDTO);
    }

    public Scene updateProject(ProjectDTO projectDTO) {
        this.projectController.updateProject(projectDTO);
        return this.getMainScene();
    }
}
