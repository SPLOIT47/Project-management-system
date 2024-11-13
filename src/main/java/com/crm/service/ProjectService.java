package com.crm.service;

import com.crm.enums.TaskStatus;
import com.crm.model.dto.ProjectDTO;
import com.crm.model.entity.Notification;
import com.crm.model.entity.Project;
import com.crm.model.entity.Task;
import com.crm.model.entity.AppUser;
import com.crm.model.repository.ProjectRepository;
import com.crm.model.repository.TaskRepository;
import com.crm.model.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final NotificationService notificationService;
    private final UserRepository userRepository;

    @Autowired
    public ProjectService(
            ProjectRepository repository,
            TaskRepository taskRepository,
            NotificationService notificationService, UserRepository userRepository) {
        this.projectRepository = repository;
        this.taskRepository = taskRepository;
        this.notificationService = notificationService;
        this.userRepository = userRepository;
    }

    @Transactional
    public void assignTaskToUsers(Task task, Collection<AppUser> users) {
        String message = "New task assigned: " + task.getName();
        Notification notification = this.notificationService.createNotification(message);

        this.notificationService.sendNotificationAsync(notification, users);
    }

    public Task createNewTask(
            Project project,
            String name,
            String description,
            int priority) {
        Task task =  Task.builder()
                .Name(name)
                .project(project)
                .Description(description)
                .priority(priority)
                .status(TaskStatus.TODO)
                .build();
        return this.taskRepository.save(task);
    }

    public Optional<Project> createNewProject(String managerUsername, String name, String description) {
        Optional<AppUser> existingUser = this.userRepository.findUserByUsername(managerUsername);
        if (existingUser.isEmpty()) {
            return Optional.empty();
        }

        Project project = Project.builder()
                .name(name)
                .description(description)
                .manager(existingUser.get())
                .build();

        return Optional.of(this.projectRepository.save(project));
    }

    public Project addTaskToProject(Project project, Task task) {
        project.getTasks().add(task);
        return this.projectRepository.save(project);
    }

    public Project addTasksToProject(Project project, Collection<Task> tasks) {
        tasks.forEach(task -> project.getTasks().add(task));
        return this.projectRepository.save(project);
    }

    public Stream<ProjectDTO> getProjectDTOByUsername(String username) {
        List<ProjectDTO> projects = this.projectRepository.getProjectNamesRolesByUsername(username);

        return projects.stream()
                .map(project -> {
                    List<AppUser> users = this.projectRepository.getUsersByProjectId(project.id());
                    List<Task> tasks = this.projectRepository.getTasksByProjectId(project.id());
                    return new ProjectDTO(
                            project.id(),
                            project.projectName(),
                            project.role(),
                            project.username(),
                            project.description(),
                            users,
                            tasks
                    );
                });
    }

    public void updateProject(ProjectDTO projectDTO) {
        Optional<Project> existingProject = this.projectRepository.getProjectsById(projectDTO.id());
        existingProject.ifPresent(project -> {
            project.setName(projectDTO.projectName());
            project.setDescription(projectDTO.description());
            project.setTasks(projectDTO.tasks());
            project.setCustomers(project.getCustomers());
        });
        this.projectRepository.save(existingProject.orElse(new Project(projectDTO)));
    }
}
