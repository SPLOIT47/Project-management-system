package com.crm.application.service;

import com.crm.application.dto.ProjectDTO;
import com.crm.application.dto.TaskDto;
import com.crm.application.dto.UserDTO;
import com.crm.application.mapper.Mapper;
import com.crm.domain.entity.Notification;
import com.crm.domain.entity.Project;
import com.crm.domain.entity.Task;
import com.crm.domain.entity.User;
import com.crm.domain.entity.mapping.UserRoleMapping;
import com.crm.domain.enums.UserRole;
import com.crm.domain.repository.ProjectRepository;
import com.crm.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskService taskService;
    private final NotificationService notificationService;
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public ProjectService(
            ProjectRepository repository,
            TaskService taskService,
            NotificationService notificationService,
            UserService userService,
            UserRepository userRepository) {
        this.projectRepository = repository;
        this.taskService = taskService;
        this.notificationService = notificationService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public void assignTaskToUsers(Task task, Collection<User> users) {
        String message = "New task assigned: " + task.getName();
        Notification notification = this.notificationService.createNotification(message);

        this.notificationService.sendNotificationAsync(notification, users);
    }

    public Stream<ProjectDTO> getProjectDTOByProjectName(String username) {
        List<Project> projects = this.projectRepository.getProjectNamesRolesByUsername(username);

        return projects.stream().map(project -> Mapper.map(project, ProjectDTO.class));
    }

    @Transactional
    public void updateProject(ProjectDTO projectDTO) {
        Project existringProject = this.projectRepository.getProjectById(projectDTO.id()).orElseThrow();

        User manager = this.userRepository.findUserByUsername(projectDTO
                        .users()
                        .values()
                        .stream()
                        .filter(role -> role == UserRole.Manager)
                        .findFirst()
                        .toString())
                .orElseThrow();

        Collection<Task> tasks = existringProject.getTasks();
        Collection<TaskDto> taskDtos = projectDTO.tasks();
        Collection<Task> mergedTasks = this.taskService
                .mergeTasks(taskDtos, tasks, existringProject)
                .toList();


        // FIXME: need to merge a map of users with roles, not only by id, coz now we lost role of each user in project

        Collection<UserRoleMapping> mergedUsers = this.userService.mergeUserRoles(projectDTO.users(), existringProject.getProjectRoles()).toList();

        existringProject.setName(projectDTO.projectName());
        existringProject.setDescription(projectDTO.description());
        existringProject.setTasks(mergedTasks);
        existringProject.setProjectRoles((Set<UserRoleMapping>) mergedUsers);
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

    public void createProject(String managerName, String projectName, String description) {
        User manager = this.userRepository.findUserByUsername(managerName).orElseThrow();
        Project project = new Project(projectName, description, manager);
        this.projectRepository.save(project);
    }

    public void addUserToProject(User manager, String projectName, String username) {
        Optional<Project> existingProject = this.projectRepository
                .getProjectByManagerAndProjectName(manager, projectName);

        Optional<User> userToAdd = this.userRepository.findUserByUsername(username);

        if (existingProject.isEmpty() || userToAdd.isEmpty()) {
            return;
        }

        existingProject.get().addUser(userToAdd.get(), UserRole.Employee);
    }
}