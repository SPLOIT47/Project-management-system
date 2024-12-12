package com.crm.application.service;

import com.crm.application.dto.TaskDto;
import com.crm.domain.entity.Project;
import com.crm.domain.entity.Task;
import com.crm.domain.service.TaskManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

@Service
public class TaskService {
    private final TaskManager taskManager;

    @Autowired
    private TaskService(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public Stream<Task> mergeTasks(Collection<TaskDto> newTasks, Collection<Task> oldTasks, Project project) {
        Collection<Task> mergedTasks = new ArrayList<>();

        newTasks.stream()
                .filter(taskDto -> !oldTasks.stream()
                        .map(Task::getName)
                        .toList()
                        .contains(taskDto.taskName()))
                .forEach(taskDto -> {
                    Task task = this.taskManager.createTask(
                            taskDto.taskName(),
                            taskDto.description(),
                            project,
                            taskDto.priority());
                    mergedTasks.add(task);
                });

        oldTasks.stream().filter(task -> !newTasks.stream()
                        .map(TaskDto::taskName)
                        .toList()
                        .contains(task.getName()))
                .forEach(mergedTasks::remove);

        return mergedTasks.stream();
    }
}
