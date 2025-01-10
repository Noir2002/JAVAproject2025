package com.isep.javafxdemo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class ProjetWindow extends VBox {
    private ListView<Projet> projectListView;
    private VBox detailLayout;

    public ProjetWindow() {
        projectListView = new ListView<>();
        detailLayout = new VBox(10);
        detailLayout.setPadding(new Insets(10));

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

        HBox mainLayout = new HBox(scrollPane, detailLayout);
        mainLayout.setAlignment(Pos.CENTER);

        this.setSpacing(10);
        this.setPadding(new Insets(10));
        this.getChildren().addAll(new Text("Projects"), mainLayout, buttonBox);
    }

    private void createProject() {
        ProjectDetailWindow projectDetailWindow = new ProjectDetailWindow(new Projet(0, "", "", 0), true);
        projectDetailWindow.show();
    }

    private void editProject() {
        Projet selectedProject = projectListView.getSelectionModel().getSelectedItem();
        if (selectedProject != null) {
            ProjectDetailWindow projectDetailWindow = new ProjectDetailWindow(selectedProject, false);
            projectDetailWindow.show();
        }
    }

    private void deleteProject() {
        Projet selectedProject = projectListView.getSelectionModel().getSelectedItem();
        if (selectedProject != null) {
            Projet.deleteProjet(selectedProject);
            projectListView.getItems().remove(selectedProject);
            System.out.println("Delete Project: " + selectedProject.getNom());
        }
    }
}