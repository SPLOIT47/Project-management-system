package com.crm.domain.repository;

import com.crm.domain.entity.Project;
import com.crm.domain.entity.Task;
import com.crm.domain.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository {

    Project save(Project project);

    Optional<Project> findById(UUID id);

    void delete(Project project);

    Optional<Project> getProjectsById(UUID id);

    List<String> getProjectNamesByManagerUsername(String username);

    List<Project> getProjectNamesRolesByUsername(String username);

    List<User> getProjectUsersByProjectNameAndManagerName(String projectName, String managerName);

    List<User> getUsersByProjectId(UUID projectId);

    List<Task> getTasksByProjectId(UUID projectId);
}
