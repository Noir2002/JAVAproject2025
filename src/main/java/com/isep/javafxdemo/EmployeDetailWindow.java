package com.isep.javafxdemo;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EmployeDetailWindow {
    private Stage stage;
    private Employe employe;
    private TextField idField;
    private TextField nomField;
    private TextField roleField;
    private ListView<Projet> HistoriquesProjetListView;

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

        HistoriquesProjetListView = new ListView<>();
        ArrayList<Projet> HistoriquesProjet = new ArrayList<>();
        if(Projet.getProjets()==null){
            Projet.setProjets();
        }
        for (Projet projet : Projet.getProjets()) {
            if (projet.getMembresProjet().contains(employe)) {
                HistoriquesProjet.add(projet);                
            }
        }
        HistoriquesProjetListView.getItems().addAll(HistoriquesProjet);
        HistoriquesProjetListView.setCellFactory(param -> new javafx.scene.control.ListCell<Projet>() {
            @Override
            protected void updateItem(Projet projet, boolean empty) {
                super.updateItem(projet, empty);
                if (empty || projet == null) {
                    setText(null);
                } else {
                    setText(projet.getId() + " - " + projet.getNom());
                }
            }
        });


        layout.getChildren().addAll(idLabel, idField, nomLabel, nomField, roleLabel, roleField, HistoriquesProjetListView, saveButton);

        Scene scene = new Scene(layout, 300, 300);
        stage.setScene(scene);
    }

    public void saveButton(){
        employe.setId(Integer.parseInt(idField.getText()));
        employe.setNom(nomField.getText());
        employe.setRole(roleField.getText());
        stage.close();
        MainApp.getRoot().setCenter(new EmployeWindow());        
    }

    public void show() {
        stage.show();
    }
}
