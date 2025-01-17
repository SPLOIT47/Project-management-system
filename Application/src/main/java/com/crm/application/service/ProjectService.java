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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
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
        Project existringProject = this.projectRepository.getProjectById(projectDTO.getId()).orElseThrow();

        existringProject.setName(projectDTO.getProjectName());
        existringProject.setDescription(projectDTO.getDescription());

        this.projectRepository.save(existringProject);
    }

    public Stream<UserDTO> getUsersByUsername(String projectName, String managerName) {
        return this.projectRepository
                .getProjectUsersByProjectNameAndManagerName(projectName, managerName)
                .stream()
                .map(User -> Mapper.map(User.getUser(), UserDTO.class))
                .toList()
                .stream();
    }

    @Transactional
    public void createProject(String managerName, String projectName, String description) {
        User manager = this.userRepository.findUserByUsername(managerName).orElseThrow();
        Project project = new Project(projectName, description, manager);
        this.projectRepository.save(project);
    }

    @Transactional
    public boolean addUserToProject(User manager, String projectName, String username) {
        Optional<Project> existingProject = this.projectRepository
                .getProjectByManagerAndProjectName(manager, projectName);

        Optional<User> userToAdd = this.userRepository.findUserByUsername(username);

        if (existingProject.isEmpty() || userToAdd.isEmpty()) {
            return false;
        }

        existingProject.get().addUser(userToAdd.get(), UserRole.Employee);
        this.projectRepository.save(existingProject.get());
        return true;
    }

    public boolean removeUserProject(String projectName, User manager, String username) {
        Optional<Project> existingProject = this.projectRepository
                .getProjectByManagerAndProjectName(manager, projectName);

        Optional<User> userToDelete = this.userRepository.findUserByUsername(username);

        if (existingProject.isEmpty() || userToDelete.isEmpty()) {
            return false;
        }

        existingProject.get().removeUser(userToDelete.get());
        this.projectRepository.save(existingProject.get());
        return true;
    }
}