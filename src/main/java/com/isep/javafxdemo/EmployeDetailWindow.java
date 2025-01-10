package com.isep.javafxdemo;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EmployeDetailWindow {
    private Stage stage;
    private Employe employe;
    TextField idField;
    TextField nomField;
    TextField roleField;

    public EmployeDetailWindow(Employe employe) {
        this.employe = employe;
        stage = new Stage();
        stage.setTitle("Employe Details");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> saveButton());

        Label idLabel = new Label("ID: ");
        Label nomLabel = new Label("Nom: ");
        Label roleLabel = new Label("Role: ");
        idField = new TextField(String.valueOf(employe.getId()));
        nomField  = new TextField(employe.getNom());
        roleField = new TextField(employe.getRole());

        layout.getChildren().addAll(idLabel, idField, nomLabel, nomField, roleLabel, roleField, saveButton);

        Scene scene = new Scene(layout, 300, 300);
        stage.setScene(scene);
    }

    public void saveButton(){
        employe.setId(Integer.parseInt(idField.getText()));
        employe.setNom(nomField.getText());
        employe.setRole(roleField.getText());
    }

    public void show() {
        stage.show();
    }
}
