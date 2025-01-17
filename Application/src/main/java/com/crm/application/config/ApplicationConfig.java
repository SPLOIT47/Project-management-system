package com.crm.application.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "com.crm.application.service",
        "com.crm.application.controller",
        "com.crm.application.session"})
public class ApplicationConfig {
}
