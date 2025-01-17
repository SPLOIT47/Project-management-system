package com.crm.application.controller;

import com.crm.application.dto.ProjectDTO;
import com.crm.application.dto.UserDTO;
import com.crm.application.service.ProjectService;
import com.crm.application.session.SessionManager;
import com.crm.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Stream;

@Controller
public class ProjectController {
    private final ProjectService projectService;
    private final SessionManager sessionManager;

    @Autowired
    public ProjectController(
            ProjectService projectService,
            SessionManager sessionManager) {
        this.projectService = projectService;
        this.sessionManager = sessionManager;
    }

    public void updateProject(ProjectDTO projectDTO) {
        this.projectService.updateProject(projectDTO);
    }

    public Stream<ProjectDTO> getProjectsByUsername(String username) {
        return this.projectService.getProjectDTOByProjectName(username);
    }

    public void createProject(String managerName, String projectName, String description) {
        this.projectService.createProject(managerName, projectName, description);
    }

    public Stream<UserDTO> getUsersByProjectName(String projectName) {
        String manager = this.sessionManager.getCurrentUser().getUsername();
        return this.projectService.getUsersByUsername(projectName, manager);
    }

    public boolean addUserToProject(String projectName, String username) {
        Stream<UserDTO> users = this.getUsersByProjectName(projectName);
        List<UserDTO> userWithSameName = users
                .filter(dto -> dto
                        .getUsername()
                        .equals(username))
                .toList();

        if (!userWithSameName.isEmpty()) {
            return false;
        }

        User manager = this.sessionManager.getCurrentUser();
        return this.projectService.addUserToProject(manager, projectName, username);
    }

    public boolean removeUserFromProject(ProjectDTO projectDto, UserDTO userToDeleteDto) {
        return this.projectService.removeUserProject(projectDto.getProjectName(), this.sessionManager.getCurrentUser(), userToDeleteDto.getUsername());
    }
}
