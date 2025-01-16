package com.isep.javafxdemo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class EmployeWindowEmploye extends VBox {
    private ListView<Employe> employeListView;

    public EmployeWindowEmploye() {
        employeListView = new ListView<>();

        // 设置员工列表视图
        employeListView.getItems().addAll(Employe.getEmployes());
        employeListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Employe employe, boolean empty) {
                super.updateItem(employe, empty);
                if (empty || employe == null) {
                    setText(null);
                } else {
                    //setText(null);
                    setText(employe.getNom());
                }
            }
        });

        employeListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Employe selectedEmploye = employeListView.getSelectionModel().getSelectedItem();
                if (selectedEmploye != null) {
                    new EmployeDetailWindowEmploye(selectedEmploye).show();
                }
            }
        });


        // 布局设置
        ScrollPane scrollPane = new ScrollPane(employeListView);
        scrollPane.setFitToWidth(true);

        this.setSpacing(10);
        this.setPadding(new Insets(10));
        this.getChildren().addAll(new Text("Employes"), scrollPane);
    }


    public EmployeWindowEmploye getWindow() {
        return this;
    }  
}
