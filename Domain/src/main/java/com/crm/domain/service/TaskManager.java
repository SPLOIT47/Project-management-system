package com.crm.domain.service;

import com.crm.domain.entity.Project;
import com.crm.domain.entity.Task;
import com.crm.domain.entity.User;
import com.crm.domain.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TaskManager {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskManager(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(String name, String description, Project project, int priority) {
        Task task = new Task(project, name, description, priority);
        return this.taskRepository.save(task);
    }

    public Task updateTask(Task task) {
        return this.taskRepository.save(task);
    }

    public Optional<Task> assignTaskToUser(UUID id, User user) {
        Optional<Task> task = this.taskRepository.findById(id);

        task.ifPresent(value -> {
            value.getAssignedUsers().add(user);
           this.taskRepository.save(value);
        });

        return task;
    }
}
