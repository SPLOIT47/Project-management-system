package com.crm.view.layout;

import com.crm.view.handler.ActionHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@Component
public class TaskLayout{

    private final ObjectProvider<ActionHandler> actionControllerProvider;

    public TaskLayout(ObjectProvider<ActionHandler> actionControllerProvider) {
        this.actionControllerProvider = actionControllerProvider;
    }

    public Scene createScene() {
        BorderPane taskLayout = new BorderPane();

        Label taskLabel = new Label("Task Management");
        HBox topPanel = new HBox(taskLabel);
        topPanel.setSpacing(10);

        VBox leftPanel = new VBox();
        Button addTaskButton = new Button("Add Task");
        Button editTaskButton = new Button("Edit Task");
        leftPanel.getChildren().addAll(addTaskButton, editTaskButton);
        leftPanel.setSpacing(10);

        VBox centerPanel = new VBox();
        Label detailsLabel = new Label("Task Details:");
        centerPanel.getChildren().add(detailsLabel);

        taskLayout.setTop(topPanel);
        taskLayout.setLeft(leftPanel);
        taskLayout.setCenter(centerPanel);

        return new Scene(taskLayout, 600, 400);
    }
}
