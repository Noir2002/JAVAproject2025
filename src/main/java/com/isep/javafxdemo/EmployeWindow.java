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

public class EmployeWindow {
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

        window = new VBox();
        window.getChildren().add(new Text("Employe Window"));
        // 添加更多组件和布局
    }

    public VBox getWindow() {
        return window;
    }
}