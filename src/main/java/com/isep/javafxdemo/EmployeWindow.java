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

public class EmployeWindow extends VBox {
    private ListView<Employe> employeListView;

    public EmployeWindow() {
        employeListView = new ListView<>();

        // 示例员工
        Employe m = new Employe(1, "Employe A", "manager");

        // 设置员工列表视图
        employeListView.getItems().addAll(Employe.getEmployes());
        employeListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Employe employe, boolean empty) {
                super.updateItem(employe, empty);
                if (empty || employe == null) {
                    setText(null);
                } else {
                    setText(employe.getNom());
                }
            }
        });

        employeListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Employe selectedEmploye = employeListView.getSelectionModel().getSelectedItem();
                if (selectedEmploye != null) {
                    new EmployeDetailWindow(selectedEmploye).show();
                }
            }
        });

        // 创建按钮
        Button createButton = new Button("Create");
        Button deleteButton = new Button("Delete");

        createButton.setOnAction(e -> createEmploye());
        deleteButton.setOnAction(e -> deleteEmploye());

        // 布局设置
        ScrollPane scrollPane = new ScrollPane(employeListView);
        scrollPane.setFitToWidth(true);

        HBox buttonBox = new HBox(10, createButton, deleteButton);
        buttonBox.setAlignment(Pos.CENTER);

        this.setSpacing(10);
        this.setPadding(new Insets(10));
        this.getChildren().addAll(new Text("Employes"), scrollPane, buttonBox);
    }

    public void createEmploye() {
        // 实现新建的逻辑
        System.out.println("Create Employe");
    }

    public void deleteEmploye() {
        // 实现删除的逻辑
        Employe selectedEmploye = employeListView.getSelectionModel().getSelectedItem();
        if (selectedEmploye != null) {
            Employe.deleteEmploye(selectedEmploye);
            employeListView.getItems().remove(selectedEmploye);
            System.out.println("Delete Employe: " + selectedEmploye.getNom());
        }
    }

    public EmployeWindow getWindow() {
        return this;
    }
}