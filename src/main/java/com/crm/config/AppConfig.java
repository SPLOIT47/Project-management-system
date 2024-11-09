package com.crm.config;

import com.crm.service.NotificationService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.crm")
public class AppConfig {

    private final NotificationService notificationService;

    public AppConfig(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
}
