package com.crm.domain.repository;

import com.crm.domain.entity.Project;
import com.crm.domain.entity.Task;
import com.crm.domain.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository {

    Project save(Project project);

    Optional<Project> findById(UUID id);

    void delete(Project project);

    Optional<Project> getProjectById(UUID id);

    Optional<Project> getProjectByManagerAndProjectName(User manager, String projectName);

    List<String> getProjectNamesByManagerUsername(String username);

    List<Project> getProjectNamesRolesByUsername(String username);

    List<User> getProjectUsersByProjectNameAndManagerName(String projectName, String managerName);

    List<User> getUsersByProjectId(UUID projectId);

    List<Task> getTasksByProjectId(UUID projectId);
}
