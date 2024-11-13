package com.crm.model.repository;

import com.crm.model.dto.ProjectDTO;
import com.crm.model.entity.AppUser;
import com.crm.model.entity.Project;
import com.crm.model.entity.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends CrudRepository<Project, UUID> {

    Optional<Project> getProjectsById(UUID id);

    @Query("SELECT  p.name " +
            "FROM  Project p " +
            "WHERE p.manager.username = :username")
    List<String> getProjectNamesByManagerUsername(@Param("username") String username);

    @Query("SELECT new com.crm.model.dto.ProjectDTO(p.id, p.name, " +
            "CASE WHEN p.manager.username = :username THEN 'Manager' ELSE 'Customer' END, " +
            ":username, p.description, null, null) " +
            "FROM Project p " +
            "LEFT JOIN p.customers c " +
            "WHERE p.manager.username = :username OR c.username = :username")
    List<ProjectDTO> getProjectNamesRolesByUsername(@Param("username") String username);

    @Query("SELECT p.customers FROM Project p WHERE p.name = :projectName")
    List<AppUser> getProjectUsersByName(@Param("projectName") String projectName);

    @Query("SELECT p.customers FROM Project p WHERE p.id = :projectId")
    List<AppUser> getUsersByProjectId(@Param("projectId") UUID projectId);

    @Query("SELECT p.tasks FROM Project p WHERE p.id = :projectId")
    List<Task> getTasksByProjectId(@Param("projectId") UUID projectId);
}
