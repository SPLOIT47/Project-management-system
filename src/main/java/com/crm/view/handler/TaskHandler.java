package com.crm.view.handler;

import com.crm.controller.AuthenticationController;
import com.crm.view.layout.TaskLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskHandler {
    private final AuthenticationController authenticationController;
    private final TaskLayout taskLayout;

    @Autowired
    public TaskHandler(
            AuthenticationController authenticationController,
            TaskLayout taskLayout) {
        this.authenticationController = authenticationController;
        this.taskLayout = taskLayout;
    }
}
