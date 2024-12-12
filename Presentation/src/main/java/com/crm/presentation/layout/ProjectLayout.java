package com.crm.presentation.layout;

import com.crm.application.dto.ProjectDTO;
import com.crm.application.dto.UserDTO;
import com.crm.application.session.SessionManager;
import com.crm.presentation.handler.ActionHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectLayout {

    private final ObjectProvider<ActionHandler> actionControllerProvider;
    private final SessionManager sessionManager;

    @Autowired
    public ProjectLayout(ObjectProvider<ActionHandler> actionControllerProvider, SessionManager sessionManager) {
        this.actionControllerProvider = actionControllerProvider;
        this.sessionManager = sessionManager;
    }

    public Scene createScene() {
        BorderPane projectLayout = new BorderPane();

        Label projectLabel = new Label("Project Management");
        HBox topPanel = new HBox(projectLabel);
        topPanel.setSpacing(10);

        VBox leftPanel = new VBox();
        Button addProjectButton = new Button("Add Project");
        Button editProjectButton = new Button("Edit Project");
        leftPanel.getChildren().addAll(addProjectButton, editProjectButton);
        leftPanel.setSpacing(10);

        VBox centerPanel = new VBox();
        Label detailsLabel = new Label("Project Details:");
        ListView<ProjectDTO> projectList = new ListView<>();
        projectList.getItems().addAll(this.getProjectDTO());

        projectList.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(ProjectDTO project, boolean empty) {
                super.updateItem(project, empty);
                if (empty || project == null) {
                    this.setText(null);
                    this.setGraphic(null);
                } else {
                    Label nameLabel = new Label(project.projectName());
                    Label roleLabel = new Label(project.managerName()
                            .equals(ProjectLayout.this.sessionManager.getCurrentUserName())
                            ? "manager" : "user");

                    nameLabel.setPadding(new Insets(0, 10, 0, 0));
                    roleLabel.setStyle("-fx-text-fill: darkgray;");

                    Region spacer = new Region();
                    HBox.setHgrow(spacer, Priority.ALWAYS);

                    HBox hbox = new HBox(nameLabel, spacer, roleLabel);
                    hbox.setAlignment(Pos.CENTER_LEFT);
                    hbox.setSpacing(10);

                    this.setGraphic(hbox);
                }
            }
        });


        centerPanel.getChildren().addAll(detailsLabel, projectList);
        projectLayout.setTop(topPanel);
        projectLayout.setLeft(leftPanel);
        projectLayout.setCenter(centerPanel);

        addProjectButton.setOnAction(actionEvent -> {
            ActionHandler actionHandler = this.actionControllerProvider.getIfAvailable();
            if (actionHandler != null) {
                actionHandler.showProjectCreatingMenu();
            }
        });

        editProjectButton.setOnAction(actionEvent -> {
            ActionHandler actionHandler = this.actionControllerProvider.getIfAvailable();
            if (actionHandler != null) {
                ProjectDTO projectDTO = projectList.getSelectionModel().getSelectedItem();
                if (projectDTO != null) {
                    actionHandler.showProjectEditingMenu(projectDTO);
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a project to edit.");
                    alert.show();
                }
            }
        });
        return new Scene(projectLayout, 600, 400);
    }

    public Stage createAdditionProjectDialog() {
        Stage dialog = new Stage();
        dialog.sizeToScene();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Add New Project");

        GridPane dialogLayout = new GridPane();
        dialogLayout.setPadding(new Insets(20));
        dialogLayout.setVgap(10);
        dialogLayout.setHgap(10);

        Label nameLabel = new Label("Project Name:");
        TextField nameField = new TextField();
        Label descriptionLabel = new Label("Description:");
        TextArea descriptionArea = new TextArea();

        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");

        dialogLayout.add(nameLabel, 0, 0);
        dialogLayout.add(nameField, 1, 0);
        dialogLayout.add(descriptionLabel, 0, 1);
        dialogLayout.add(descriptionArea, 1, 1);
        dialogLayout.add(saveButton, 0, 2);
        dialogLayout.add(cancelButton, 1, 2);

        saveButton.setOnAction(event -> {
            String projectName = nameField.getText();
            String projectDescription = descriptionArea.getText();

            if (!projectName.isEmpty()) {
                ActionHandler actionHandler = this.actionControllerProvider.getIfAvailable();
                if (actionHandler != null) {
                    actionHandler.handleAddProject(projectName, projectDescription);
                }
                dialog.close();
            } else {
                nameLabel.setStyle("-fx-text-fill: red;");
                nameLabel.setText("Project Name (required):");
            }
        });

        cancelButton.setOnAction(event -> dialog.close());

        Scene dialogScene = new Scene(dialogLayout);
        dialog.setScene(dialogScene);
        return dialog;
    }

    public Stage createEditProjectDialog(ProjectDTO projectDTO) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Edit Project");
        dialog.sizeToScene();

        GridPane dialogLayout = new GridPane();
        dialogLayout.setPadding(new Insets(20));
        dialogLayout.setVgap(10);
        dialogLayout.setHgap(10);

        Label nameLabel = new Label("Project Name:");
        TextField nameField = new TextField(projectDTO.projectName());
        Label descriptionLabel = new Label("Description:");
        TextArea descriptionArea = new TextArea(projectDTO.description());

        Label usersLabel = new Label("Users:");
        ListView<UserDTO> usersListView = new ListView<>();
        usersListView.getItems().setAll(projectDTO.users());

        Button addUserButton = new Button("Add User");
        Button removeUserButton = new Button("Remove User");

        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");

        dialogLayout.add(nameLabel, 0, 0);
        dialogLayout.add(nameField, 1, 0);
        dialogLayout.add(descriptionLabel, 0, 1);
        dialogLayout.add(descriptionArea, 1, 1);
        dialogLayout.add(usersLabel, 0, 2);
        dialogLayout.add(usersListView, 1, 2);
        dialogLayout.add(addUserButton, 0, 3);
        dialogLayout.add(removeUserButton, 1, 3);
        dialogLayout.add(saveButton, 0, 4);
        dialogLayout.add(cancelButton, 1, 4);

        saveButton.setOnAction(event -> {
            String projectName = nameField.getText();
            String projectDescription = descriptionArea.getText();

            if (!projectName.isEmpty()) {
                ActionHandler actionHandler = this.actionControllerProvider.getIfAvailable();
                if (actionHandler != null) {
                    ProjectDTO project = new ProjectDTO(
                            projectDTO.id(),
                            projectName,
                            projectDTO.managerName(),
                            projectDescription,
                            usersListView.getItems().stream().toList(),
                            projectDTO.tasks());
                   actionHandler.handleEditProject(project);
                }
                dialog.close();
            } else {
                nameLabel.setStyle("-fx-text-fill: red;");
                nameLabel.setText("Project Name (required):");
            }
        });

        cancelButton.setOnAction(event -> dialog.close());

        addUserButton.setOnAction(event -> {
            ActionHandler actionHandler = this.actionControllerProvider.getIfAvailable();
            if (actionHandler != null) {
                // actionHandler.showAddUserDialog(projectDTO, usersListView);
            }
        });

        removeUserButton.setOnAction(event -> {
            UserDTO selectedUser = usersListView.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                usersListView.getItems().remove(selectedUser);
                // actonHandler.removeUserFromProject();
            }
        });

        Scene dialogScene = new Scene(dialogLayout);
        dialog.setScene(dialogScene);
        return dialog;
    }

    public void showAddUserDialog(ProjectDTO projectDTO, ListView<UserDTO> usersListView) {
        Stage addUserDialog = new Stage();
        addUserDialog.initModality(Modality.APPLICATION_MODAL);
        addUserDialog.setTitle("Add User to Project");

        VBox dialogLayout = new VBox(10);
        dialogLayout.setPadding(new Insets(20));

        Label label = new Label("Select a User to Add:");

        ListView<UserDTO> availableUsersListView = new ListView<>();
        availableUsersListView.getItems().addAll(this.getProjectUsers(projectDTO.projectName()));

        Button addButton = new Button("Add User");
        addButton.setOnAction(event -> {
            UserDTO selectedUser = availableUsersListView.getSelectionModel().getSelectedItem();
            if (selectedUser != null && !usersListView.getItems().contains(selectedUser)) {
                usersListView.getItems().add(selectedUser);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Select a valid user or the user is already added.");
                alert.show();
            }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> addUserDialog.close());

        dialogLayout.getChildren().addAll(label, availableUsersListView, addButton, cancelButton);

        Scene dialogScene = new Scene(dialogLayout, 300, 250);
        addUserDialog.setScene(dialogScene);
        addUserDialog.show();
    }


    private List<ProjectDTO> getProjectDTO() {
        ActionHandler actionHandler = this.actionControllerProvider.getIfAvailable();
        if (actionHandler != null) {
            return actionHandler.
                    getUserProjectDTO(
                            this.sessionManager.getCurrentUserName()
                    ).toList();
        }

        return new ArrayList<>();
    }

    private List<UserDTO> getProjectUsers(String projectName) {
        ActionHandler actionHandler = this.actionControllerProvider.getIfAvailable();
        if (actionHandler != null) {
            return actionHandler.getProjectUsers(projectName).toList();
        }

        return new ArrayList<>();
    }
}
