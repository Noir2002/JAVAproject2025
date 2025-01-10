package com.isep.javafxdemo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class ProjetWindow extends VBox {
    private ListView<Projet> projectListView;

    public ProjetWindow() {
        projectListView = new ListView<>();

        // 示例项目
        new Projet(1, "Project A", "2025-12-31 23:59:59", 10000);
        new Projet(2, "Project B", "2025-11-30 23:59:59", 15000);

        // 设置项目列表视图
        projectListView.getItems().addAll(Projet.getProjets());
        projectListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Projet project, boolean empty) {
                super.updateItem(project, empty);
                if (empty || project == null) {
                    setText(null);
                } else {
                    setText(project.getNom());
                }
            }
        });

        projectListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Projet selectedProject = projectListView.getSelectionModel().getSelectedItem();
                if (selectedProject != null) {
                    new ProjectDetailWindow(selectedProject).show();
                }
            }
        });

        // 创建按钮
        Button createButton = new Button("Create");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");

        createButton.setOnAction(e -> createProject());
        editButton.setOnAction(e -> editProject());
        deleteButton.setOnAction(e -> deleteProject());

        // 布局设置
        ScrollPane scrollPane = new ScrollPane(projectListView);
        scrollPane.setFitToWidth(true);

        HBox buttonBox = new HBox(10, createButton, editButton, deleteButton);
        buttonBox.setAlignment(Pos.CENTER);

        this.setSpacing(10);
        this.setPadding(new Insets(10));
        this.getChildren().addAll(new Text("Projects"), scrollPane, buttonBox);
    }

    private void createProject() {
        // 实现新建项目的逻辑
        System.out.println("Create Project");
    }

    private void editProject() {
        // 实现编辑项目的逻辑
        Projet selectedProject = projectListView.getSelectionModel().getSelectedItem();
        if (selectedProject != null) {
            System.out.println("Edit Project: " + selectedProject.getNom());
        }
    }

    private void deleteProject() {
        // 实现删除项目的逻辑
        Projet selectedProject = projectListView.getSelectionModel().getSelectedItem();
        if (selectedProject != null) {
            Projet.deleteProjet(selectedProject);
            projectListView.getItems().remove(selectedProject);
            System.out.println("Delete Project: " + selectedProject.getNom());
        }
    }
}