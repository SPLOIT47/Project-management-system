package com.crm.domain.service;

import com.crm.domain.entity.Project;
import com.crm.domain.entity.Task;
import com.crm.domain.entity.User;
import com.crm.domain.entity.mapping.UserRoleMapping;
import com.crm.domain.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class ProjectManager {
    private final ProjectRepository projectRepository;
    private final UserManager userManager;

    @Autowired
    public ProjectManager(ProjectRepository projectRepository, UserManager userManager) {
        this.projectRepository = projectRepository;
        this.userManager = userManager;
    }

    public Project createNewProject(String projectName, String description, User manager) {
        Project project = new Project(projectName, description, manager);
        return this.projectRepository.save(project);
    }

    public Project addTaskToProject(Project project, Task task) {
        project.getTasks().add(task);
        return this.projectRepository.save(project);
    }

    public Project addTasksToProject(Project project, Collection<Task> tasks) {
        tasks.forEach(task -> project.getTasks().add(task));
        return this.projectRepository.save(project);
    }

    public Project updateProject(Project project) {
        return this.projectRepository.save(project);
    }

    public Optional<Project> updateProjectTasks(UUID id, Collection<Task> tasks) {
        Optional<Project> project = this.projectRepository.findById(id);

        if (project.isPresent()) {
            project.get().setTasks(tasks);
            return Optional.of(this.projectRepository.save(project.get()));
        }

        return Optional.empty();
    }

    public Optional<Project> updateProjectUsers(UUID id, Collection<UserRoleMapping> users) {
        Optional<Project> project = this.projectRepository.findById(id);

        project.ifPresent(value -> {
            value.setProjectRoles((Set<UserRoleMapping>) users);
            this.projectRepository.save(value);
        });

        return project;
    }

    public void deleteProject(Project project) {
        this.projectRepository.delete(project);
    }
}
