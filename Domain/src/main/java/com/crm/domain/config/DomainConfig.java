package com.crm.domain.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.crm.domain.service")
public class DomainConfig {
    public static AnnotationConfigApplicationContext configure(AnnotationConfigApplicationContext context) {
        context.register(DomainConfig.class);
        context.refresh();
        return context;
    }
}
