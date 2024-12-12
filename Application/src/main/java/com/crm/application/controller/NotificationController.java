package com.crm.application.controller;

import com.crm.domain.entity.User;
import org.springframework.stereotype.Controller;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@Controller
public class NotificationController {
    public void sendNotificationsToUser(Collection<User> appUsers, String message) {

    }

    private CompletableFuture<Void> asyncSendNotificationToUser(Collection<User> appUsers, String message) {
        return CompletableFuture.runAsync(() -> {
            for (User appUser : appUsers) {

            }
        });
    }
}
