package com.isep.javafxdemo;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Sidebar sidebar = new Sidebar();
        root.setLeft(sidebar.getSidebar());

        // 默认显示Employe窗口
        EmployeWindow employeWindow = new EmployeWindow();
        root.setCenter(employeWindow.getWindow());

        sidebar.setNavigationHandler((window) -> root.setCenter(window));

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Collaborative task management application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}