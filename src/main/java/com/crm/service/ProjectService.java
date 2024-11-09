package com.crm.service;

import com.crm.enums.TaskStatus;
import com.crm.model.entity.Notification;
import com.crm.model.entity.Project;
import com.crm.model.entity.Task;
import com.crm.model.entity.user.AppUser;
import com.crm.model.repository.ProjectRepository;
import com.crm.model.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final TaskRepository taskRepository;

    private final NotificationService notificationService;

    @Autowired
    public ProjectService(
            ProjectRepository repository,
            TaskRepository taskRepository,
            NotificationService notificationService) {
        this.projectRepository = repository;
        this.taskRepository = taskRepository;
        this.notificationService = notificationService;
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

    public Project createNewProject(AppUser manager, String name, String description) {
        Project project = Project.builder()
                .name(name)
                .description(description)
                .manager(manager)
                .build();
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
}
