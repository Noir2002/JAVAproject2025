package com.isep.javafxdemo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class ProjetWindowEmploye extends VBox {
    private ListView<Projet> projectListView;
    private VBox detailLayout;

    public ProjetWindowEmploye() {
        projectListView = new ListView<>();
        detailLayout = new VBox(10);
        detailLayout.setPadding(new Insets(10));

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
        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> editProject());


        // 布局设置
        ScrollPane scrollPane = new ScrollPane(projectListView);
        scrollPane.setFitToWidth(true);

        HBox buttonBox = new HBox(10, editButton);
        buttonBox.setAlignment(Pos.CENTER);

        HBox mainLayout = new HBox(scrollPane, detailLayout);
        mainLayout.setAlignment(Pos.CENTER);

        this.setSpacing(10);
        this.setPadding(new Insets(10));
        this.getChildren().addAll(new Text("Projects"), mainLayout, buttonBox);
    }


    private void editProject() {
        Projet selectedProject = projectListView.getSelectionModel().getSelectedItem();
        if (selectedProject != null) {
            ProjectDetailWindowEmploye projectDetailWindow = new ProjectDetailWindowEmploye(selectedProject, false);
            projectDetailWindow.show();
        }
    }

}
