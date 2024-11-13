package com.crm;

import com.crm.view.View;

public class Main {
    public static void main(String[] args) {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
//        context.register(AppConfig.class);
//        context.refresh();
//
//        AppUser user1 = new AppUser();
//        user1.setUsername("Alex");
//        user1.setPassword("123");
//        UserRepository userRepository = context.getBean(UserRepository.class);
//        userRepository.save(user1);
//
//        TaskRepository taskRepository = context.getBean(TaskRepository.class);
//        taskRepository.save(task);
//
//        ArrayList<AppUser> users = new ArrayList<>();
//        users.add(user1);
//        projectService.assignTaskToUsers(task, users);
//
//        AppUser exUser = userRepository.findById(user1.getId()).orElseThrow();
//
//        for (Notification n : exUser.getNotifications()) {
//            System.out.println(n.getMessage());
//        }
//        context.close();

        View.launch(View.class, args);
    }
}
