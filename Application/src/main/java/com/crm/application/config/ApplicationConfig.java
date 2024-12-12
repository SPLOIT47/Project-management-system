package com.crm.application.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.crm.application.service")
@ComponentScan("com.crm.application.controller")
@ComponentScan("com.crm.application.session")
public class ApplicationConfig {
}
