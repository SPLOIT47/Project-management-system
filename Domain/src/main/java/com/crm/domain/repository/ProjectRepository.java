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

public interface ProjectRepository extends CrudRepository<Project, UUID> {

    Optional<Project> getProjectsById(UUID id);

    @Query("SELECT  p.name " +
            "FROM  Project p " +
            "WHERE p.manager.username = :username")
    List<String> getProjectNamesByManagerUsername(@Param("username") String username);

    @Query("SELECT DISTINCT p " +
            "FROM Project p " +
            "WHERE p.manager.username = :username " +
            "OR :username = ANY (SELECT u.username FROM p.customers u)")
    List<Project> getProjectNamesRolesByUsername(@Param("username") String username);

    @Query("SELECT p.customers " +
            "FROM Project p " +
            "WHERE p.name = :projectName AND p.manager.username = :managerName")
    List<User> getProjectUsersByProjectNameAndManagerName(
            @Param("projectName") String projectName,
            @Param("managerName") String managerName);

    @Query("SELECT p.customers FROM Project p WHERE p.id = :projectId")
    List<User> getUsersByProjectId(@Param("projectId") UUID projectId);

    @Query("SELECT p.tasks FROM Project p WHERE p.id = :projectId")
    List<Task> getTasksByProjectId(@Param("projectId") UUID projectId);
}
