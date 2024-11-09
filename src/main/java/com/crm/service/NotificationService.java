package com.crm.service;

import com.crm.model.entity.Notification;
import com.crm.model.entity.user.AppUser;
import com.crm.model.repository.NotificationRepository;
import jakarta.annotation.PreDestroy;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    private final AppUserService appUserService;

    private final ExecutorService executorService;

    @Autowired
    public NotificationService(
            NotificationRepository notificationRepository,
            AppUserService appUserService) {
        this.notificationRepository = notificationRepository;
        this.appUserService = appUserService;
        this.executorService = Executors.newFixedThreadPool(10);
    }

    @Transactional
    public Notification saveNotification(Notification notification) {
        return this.notificationRepository.save(notification);
    }

    @Transactional
    public Notification createNotification(String message) {
        Notification notification = Notification.builder().message(message).build();
        return this.saveNotification(notification);
    }

    public void sendNotificationAsync(Notification notification, Collection<AppUser> users) {
        for (AppUser user : users) {
            Runnable task = () -> this.appUserService.updateUser(user, notification);
            this.executorService.submit(task);
        }
    }

    @PreDestroy
    public void shutdown() {
        try {
            this.executorService.shutdown();
            if (!this.executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                this.executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            this.executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}