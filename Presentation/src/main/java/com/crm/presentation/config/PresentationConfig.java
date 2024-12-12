package com.crm.presentation.config;

import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Setter
@Configuration
@ComponentScan(basePackages = "com.crm.presentation")
public class PresentationConfig {
    private Stage primaryStage;

    @Bean
    public Stage primaryStage() {
        return this.primaryStage;
    }
}
