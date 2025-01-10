package com.isep.javafxdemo;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProjectDetailWindow {
    private Stage stage;
    private Projet project;

    public ProjectDetailWindow(Projet project) {
        this.project = project;
        stage = new Stage();
        stage.setTitle("Project Details");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label idLabel = new Label("ID: " + project.getId());
        Label nameLabel = new Label("Name: " + project.getNom());
        Label dateLimitLabel = new Label("Date Limit: " + project.getDateLimit());
        Label budgetLabel = new Label("Budget: " + project.getBudget());
        Label realCostLabel = new Label("Real Cost: " + project.getRealCost());

        layout.getChildren().addAll(idLabel, nameLabel, dateLimitLabel, budgetLabel, realCostLabel);

        Scene scene = new Scene(layout, 300, 200);
        stage.setScene(scene);
    }

    public void show() {
        stage.show();
    }
}