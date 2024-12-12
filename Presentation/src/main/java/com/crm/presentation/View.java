package com.crm.presentation;

import com.crm.application.config.ApplicationConfig;
import com.crm.domain.config.DomainConfig;
import com.crm.infrastructure.dataaccess.config.JpaConfig;
import com.crm.presentation.config.PresentationConfig;
import com.crm.presentation.handler.ActionHandler;
import com.crm.presentation.layout.LoginLayout;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.crm.presentation.handler")
public class View extends Application {
    @Override
    public void start(Stage primaryStage) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(JpaConfig.class,  ApplicationConfig.class, PresentationConfig.class, DomainConfig.class);
        context.refresh();

        PresentationConfig presentationConfig = context.getBean(PresentationConfig.class);
        presentationConfig.setPrimaryStage(primaryStage);

        ActionHandler actionHandler = context.getBean(ActionHandler.class);
        actionHandler.setStage(primaryStage);

        LoginLayout loginLayout = context.getBean(LoginLayout.class);
        primaryStage.setScene(loginLayout.createScene());
        primaryStage.show();
    }
}
