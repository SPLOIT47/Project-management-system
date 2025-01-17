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

}
