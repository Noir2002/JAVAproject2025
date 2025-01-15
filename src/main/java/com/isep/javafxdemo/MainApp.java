package com.isep.javafxdemo;

import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {
    private static BorderPane root;

    @Override
    public void start(Stage primaryStage) {
        root = new BorderPane();
        Sidebar sidebar = new Sidebar();
        root.setLeft(sidebar.getSidebar());

        // 默认显示Employe窗口
        EmployeWindow employeWindow = new EmployeWindow();
        root.setCenter(employeWindow);

        sidebar.setNavigationHandler((window) -> root.setCenter(window));

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Collaborative Task Management Application");
        primaryStage.setScene(scene);
        primaryStage.show();
        // 检测到该窗口关闭时，更新csv文件
        primaryStage.setOnCloseRequest(event -> {
            try {
                DataHandler.saveData();
                System.out.println("Data saved before MainApp closes.");
            } catch (IOException ex) {
                System.out.println("Error saving data on close.");
                ex.printStackTrace();
            }
        });
    }
    

    public static BorderPane getRoot()  {
            return root;
        }

    public static void main(String[] args) {
        launch(args);
    }
}