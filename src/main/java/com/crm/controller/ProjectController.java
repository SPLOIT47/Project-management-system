package com.crm.controller;

import com.crm.model.dto.ProjectDTO;
import com.crm.model.entity.Project;
import com.crm.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;
import java.util.stream.Stream;

@Controller
public class ProjectController {
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    public Optional<Project> createProject(String managerUsername , String name, String description) {
        return this.projectService.createNewProject(managerUsername, name, description);
    }

    public void updateProject(ProjectDTO projectDTO) {
        this.projectService.updateProject(projectDTO);
    }

    public Stream<ProjectDTO> getProjectNamesByUsername(String username) {
        return this.projectService.getProjectDTOByUsername(username);
    }
}
