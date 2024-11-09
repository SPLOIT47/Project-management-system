package com.crm.controller;

import com.crm.model.entity.Project;
import com.crm.model.entity.Task;
import com.crm.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Collection;
import java.util.UUID;

@Controller
public class ProjectController {
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    public Project createProject(String name, String description, Collection<Task> tasks) {
        return Project.builder()
                .name(name)
                .description(description)
                .tasks(tasks)
                .build();
    }

    public Project createProject(String name, String description) {
        return Project.builder()
                .name(name)
                .description(description)
                .build();
    }

//    public Project addTaskToProject(String projectName, Task task) {
//        return this.projectService.addTaskToProject(projectName, task);
//    }
//
//    public Project addTaskToProject(UUID projectId, Task task) {
//        return this.projectService.addTaskToProject(projectId, task);
//    }
//
//    public Project addTasksToProject(String projectName, Collection<Task> tasks) {
//        return this.projectService.addTasksToProject(projectName, tasks);
//    }
//
//    public Project addTasksToProject(UUID projectId, Collection<Task> tasks) {
//        return this.projectService.addTasksToProject(projectId, tasks);
   // }
}
