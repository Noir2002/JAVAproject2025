package com.isep.javafxdemo;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ReportWindow {
    private VBox window;

    public ReportWindow() {
        window = new VBox();
        window.getChildren().add(new Text("Report Window"));
        // 添加更多组件和布局
    }

    public VBox getWindow() {
        return window;
    }
}