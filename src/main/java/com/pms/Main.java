package com.pms;

import com.crm.application.config.ApplicationConfig;
import com.crm.application.config.MapperConfig;
import com.crm.domain.config.DomainConfig;
import com.crm.infrastructure.dataaccess.config.JpaConfig;
import com.crm.presentation.config.PresentationConfig;
import com.crm.presentation.handler.ActionHandler;
import com.crm.presentation.layout.LoginLayout;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashMap;
import java.util.Map;

@ComponentScan(basePackages = "com.crm.presentation.handler")
public class Main extends Application {

    public static void main(String[] args) {
        launch(args); // Запуск JavaFX приложения
    }

    @Override
    public void start(Stage primaryStage) {
        // Создаём Spring контекст
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(JpaConfig.class, ApplicationConfig.class, PresentationConfig.class, DomainConfig.class, MapperConfig.class);
        context.refresh();

        // Настройка PresentationConfig
        PresentationConfig presentationConfig = context.getBean(PresentationConfig.class);
        presentationConfig.setPrimaryStage(primaryStage);

        // Инициализация ActionHandler
        ActionHandler actionHandler = context.getBean(ActionHandler.class);
        actionHandler.setStage(primaryStage);

        // Установка сцены и запуск приложения
        LoginLayout loginLayout = context.getBean(LoginLayout.class);
        primaryStage.setScene(loginLayout.createScene());
        primaryStage.show();
    }
}
