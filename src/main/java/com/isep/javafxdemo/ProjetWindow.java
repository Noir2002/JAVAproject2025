package com.isep.javafxdemo;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ProjetWindow {
    private VBox window;

    public ProjetWindow() {
        window = new VBox();
        window.getChildren().add(new Text("Projet Window"));
        // 添加更多组件和布局
    }

    public VBox getWindow() {
        return window;
    }
}