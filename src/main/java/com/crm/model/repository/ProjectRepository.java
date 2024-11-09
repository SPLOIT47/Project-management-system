package com.crm.model.repository;

import com.crm.model.entity.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends CrudRepository<Project, UUID> {
    Optional<Project> findProjectByName(String name);
}
