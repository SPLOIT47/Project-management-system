package com.crm;

import com.crm.config.AppConfig;
import com.crm.model.entity.Notification;
import com.crm.model.entity.Task;
import com.crm.model.entity.user.AppUser;
import com.crm.model.repository.TaskRepository;
import com.crm.model.repository.UserRepository;
import com.crm.service.ProjectService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AppConfig.class);
        context.refresh();

        ProjectService projectService = context.getBean(ProjectService.class);
        AppUser user1 = new AppUser();
        user1.setUsername("Boba");

        Task task = new Task();
        task.setName("New Task");
        task.setDescription("This is a new task.");
        task.getAssignedUsers().add(user1);

        UserRepository userRepository = context.getBean(UserRepository.class);
        userRepository.save(user1);

        TaskRepository taskRepository = context.getBean(TaskRepository.class);
        taskRepository.save(task);

        ArrayList<AppUser> users = new ArrayList<>();
        users.add(user1);
        projectService.assignTaskToUsers(task, users);

        AppUser exUser = userRepository.findById(user1.getId()).orElseThrow();

        for (Notification n : exUser.getNotifications()) {
            System.out.println(n.getMessage());
        }

        context.close();
    }
}
