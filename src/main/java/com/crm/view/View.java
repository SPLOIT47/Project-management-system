package com.crm.view;

import com.crm.config.AppConfig;
import com.crm.view.handler.ActionHandler;
import com.crm.view.layout.LoginLayout;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class View extends Application {
    @Override
    public void start(Stage primaryStage) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig appConfig = context.getBean(AppConfig.class);
        appConfig.setPrimaryStage(primaryStage);

        ActionHandler actionHandler = context.getBean(ActionHandler.class);
        actionHandler.setStage(primaryStage);

        LoginLayout loginLayout = context.getBean(LoginLayout.class);
        primaryStage.setScene(loginLayout.createScene());
        primaryStage.show();
    }
}
