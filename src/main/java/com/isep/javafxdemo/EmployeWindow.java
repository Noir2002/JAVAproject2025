package com.isep.javafxdemo;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class EmployeWindow {
    private VBox window;

    public EmployeWindow() {
        window = new VBox();
        window.getChildren().add(new Text("Employe Window"));
        // 添加更多组件和布局
    }

    public VBox getWindow() {
        return window;
    }
}