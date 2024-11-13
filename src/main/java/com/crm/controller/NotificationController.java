package com.crm.controller;

import com.crm.model.entity.AppUser;
import org.springframework.stereotype.Controller;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@Controller
public class NotificationController {
    public void sendNotificationsToUser(Collection<AppUser> appUsers, String message) {

    }

    private CompletableFuture<Void> asyncSendNotificationToUser(Collection<AppUser> appUsers, String message) {
        return CompletableFuture.runAsync(() -> {
            for (AppUser appUser : appUsers) {

            }
        });
    }
}
