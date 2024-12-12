package com.crm.infrastructure.dataaccess.repository;

import com.crm.domain.entity.Task;
import com.crm.domain.repository.TaskRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface JpaTaskRepository extends CrudRepository<Task, UUID>, TaskRepository {
}
