package com.crm.application.service;

import com.crm.application.dto.ProjectDTO;
import com.crm.application.dto.TaskDto;
import com.crm.application.dto.UserDTO;
import com.crm.application.mapper.Mapper;
import com.crm.domain.entity.Notification;
import com.crm.domain.entity.Project;
import com.crm.domain.entity.Task;
import com.crm.domain.entity.User;
import com.crm.domain.enums.TaskStatus;
import com.crm.domain.repository.ProjectRepository;
import com.crm.domain.repository.TaskRepository;
import com.crm.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
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
            NotificationService notificationService,
            UserRepository userRepository) {
        this.projectRepository = repository;
        this.taskRepository = taskRepository;
        this.notificationService = notificationService;
        this.userRepository = userRepository;
    }

    public void assignTaskToUsers(Task task, Collection<User> users) {
        String message = "New task assigned: " + task.getName();
        Notification notification = this.notificationService.createNotification(message);

        this.notificationService.sendNotificationAsync(notification, users);
    }

    public Stream<ProjectDTO> getProjectDTOByProjectName(String username) {
        List<Project> projects = this.projectRepository.getProjectNamesRolesByUsername(username);

        return projects.stream()
                .map(project -> {
                    List<User> users = this.projectRepository.getUsersByProjectId(project.getId());
                    List<Task> tasks = this.projectRepository.getTasksByProjectId(project.getId());
                    String role = project.getManager().getUsername().equals(username) ? "manager" : "user";

                    return new ProjectDTO(
                            project.getId(),
                            project.getName(),
                            project.getManager().getUsername(),
                            project.getDescription(),
                            users.stream().map(obj -> Mapper.map(obj, UserDTO.class)).toList(),
                            tasks.stream().map(obj -> Mapper.map(obj, TaskDto.class)).toList()
                    );
                });
    }

    @Transactional
    public void updateProject(ProjectDTO projectDTO) {
        Project existringProject = this.projectRepository.getProjectsById(projectDTO.id()).orElseThrow();

        Collection<User> updatedUsers = this.userRepository.findAllByUsername(projectDTO
                .users()
                .stream()
                .map(UserDTO::username)
                .collect(Collectors.toList()));

        Set<User> existingUsers = new HashSet<>(existringProject.getCustomers());

        updatedUsers.stream()
                .filter(user -> !existingUsers.contains(user))
                .forEach(existringProject::addCustomer);

        existingUsers.stream()
                .filter(user -> !updatedUsers.contains(user))
                .forEach(existringProject::removeCustomer);

        User manager = this.userRepository.findUserByUsername(projectDTO.managerName()).orElseThrow();

        Collection<Task> tasks = existringProject.getTasks();
        Map<String, Task> existingTasksMap = tasks.stream()
                .collect(Collectors.toMap(Task::getName, task -> task));

        Collection<TaskDto> taskDtos = projectDTO.tasks();

        taskDtos.stream()
                .filter(dto -> !existingTasksMap.containsKey(dto.taskName()))
                .forEach(taskDto -> {
                    Task newTask = new Task(
                            existringProject,
                            taskDto.taskName(),
                            taskDto.description(),
                            TaskStatus.valueOf(taskDto.status()),
                            taskDto.priority());
                    existringProject.addTask(newTask);
                });

        tasks.stream()
                .filter(task -> taskDtos.stream()
                        .noneMatch(dto -> dto.taskName().equals(task.getName())))
                .forEach(existringProject::removeTask);


        existringProject.setName(projectDTO.projectName());
        existringProject.setDescription(projectDTO.description());
        existringProject.setManager(manager);
        this.projectRepository.save(existringProject);
    }

    public Stream<UserDTO> getUsersByUsername(String projectName, String managerName) {
        return this.projectRepository
                .getProjectUsersByProjectNameAndManagerName(projectName, managerName)
                .stream()
                .map(User -> Mapper.map(User, UserDTO.class))
                .toList()
                .stream();
    }
}