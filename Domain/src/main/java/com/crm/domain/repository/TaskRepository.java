package com.crm.domain.repository;

import com.crm.domain.entity.Task;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository {
    Task save(Task task);

    Optional<Task> findById(UUID id);
}
