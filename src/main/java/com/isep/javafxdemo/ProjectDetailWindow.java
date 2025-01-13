package com.isep.javafxdemo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ProjectDetailWindow {
    private Stage stage;
    private Projet project;
    private TextField nameField;
    private TextField budgetField;
    private TextField realCostField;
    private TextField dateLimitField;
    private ListView<Employe> membersListView;
    private ListView<Tache> tasksListView;
    private boolean isNewProject;

    public ProjectDetailWindow(Projet project, boolean isNewProject) {
        this.project = project;
        this.isNewProject = isNewProject;
        stage = new Stage();
        stage.setTitle(isNewProject ? "Create Project" : "Edit Project");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label idLabel = new Label("ID: " + (isNewProject ? "New" : project.getId()));
        nameField = new TextField(isNewProject ? "" : project.getNom());
        budgetField = new TextField(isNewProject ? "" : String.valueOf(project.getBudget()));
        realCostField = new TextField(isNewProject ? "" : String.valueOf(project.getRealCost()));
        dateLimitField = new TextField(isNewProject ? "" : project.getDateLimit());

        membersListView = new ListView<>();
        if (!isNewProject) {
            membersListView.getItems().addAll(project.getMembresProjet());
        }

        tasksListView = new ListView<>();
        if (!isNewProject) {
            tasksListView.getItems().addAll(project.getTaches());
        }

        Button addMemberButton = new Button("Add Member");
        Button deleteMemberButton = new Button("Delete Member");
        HBox memberButtons = new HBox(10, addMemberButton, deleteMemberButton);

        Button addTaskButton = new Button("Add Task");
        Button editTaskButton = new Button("Edit Task");
        Button deleteTaskButton = new Button("Delete Task");
        HBox taskButtons = new HBox(10, addTaskButton, editTaskButton, deleteTaskButton);

        Button saveButton = new Button("Save");
        HBox saveButtonBox = new HBox(saveButton);
        saveButtonBox.setAlignment(Pos.TOP_RIGHT);

        layout.getChildren().addAll(
                idLabel,
                new Label("Name:"), nameField,
                new Label("Budget:"), budgetField,
                new Label("Real Cost:"), realCostField,
                new Label("Date Limit:"), dateLimitField,
                new Label("Members:"), membersListView, memberButtons,
                new Label("Tasks:"), tasksListView, taskButtons,
                saveButtonBox
        );

        Scene scene = new Scene(layout, 400, 600);
        stage.setScene(scene);

        // 事件处理
        addMemberButton.setOnAction(e -> addMember());
        deleteMemberButton.setOnAction(e -> deleteMember());
        addTaskButton.setOnAction(e -> addTask());
        editTaskButton.setOnAction(e -> editTask());
        deleteTaskButton.setOnAction(e -> deleteTask());
        saveButton.setOnAction(e -> saveChanges());
    }

    private void addMember() {
        // 实现添加成员的逻辑
        System.out.println("Add Member");
    }

    private void deleteMember() {
        // 实现删除成员的逻辑
        Employe selectedMember = membersListView.getSelectionModel().getSelectedItem();
        if (selectedMember != null) {
            membersListView.getItems().remove(selectedMember);
            System.out.println("Delete Member: " + selectedMember.getNom());
        }
    }

    private void addTask() {
        // 实现添加任务的逻辑
        System.out.println("Add Task");
    }

    private void editTask() {
        // 实现编辑任务的逻辑
        Tache selectedTask = tasksListView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            System.out.println("Edit Task: " + selectedTask.getNom());
        }
    }

    private void deleteTask() {
        // 实现删除任务的逻辑
        Tache selectedTask = tasksListView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            tasksListView.getItems().remove(selectedTask);
            System.out.println("Delete Task: " + selectedTask.getNom());
        }
    }

    private void saveChanges() {
        if (isNewProject) {
            project = new Projet(Projet.getProjets().size() + 1, nameField.getText(), "", Double.parseDouble(budgetField.getText()));
        } else {
            project.setNom(nameField.getText());
            project.setBudget(Double.parseDouble(budgetField.getText()));
            project.setRealCost(Double.parseDouble(realCostField.getText()));
            project.setDateLimit(dateLimitField.getText());
            project.getMembresProjet().clear();
            project.getMembresProjet().addAll(membersListView.getItems());
            project.getTaches().clear();
            project.getTaches().addAll(tasksListView.getItems());
        }
        System.out.println("Changes saved for project: " + project.getNom());
        stage.close();
        MainApp.getRoot().setCenter(new ProjetWindow());  
    }

    public void show() {
        stage.show();
    }
}