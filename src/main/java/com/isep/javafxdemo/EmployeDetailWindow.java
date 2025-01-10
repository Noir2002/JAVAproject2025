package com.isep.javafxdemo;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EmployeDetailWindow {
    private Stage stage;
    private Employe employe;

    public EmployeDetailWindow(Employe employe) {
        this.employe = employe;
        stage = new Stage();
        stage.setTitle("Employe Details");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label idLabel = new Label("ID: " + employe.getId());
        Label nomLabel = new Label("Nom: " + employe.getNom());
        Label roleLabel = new Label("Role: " + employe.getRole());

        layout.getChildren().addAll(idLabel, nomLabel, roleLabel);

        Scene scene = new Scene(layout, 300, 200);
        stage.setScene(scene);
    }

    public void show() {
        stage.show();
    }
}
