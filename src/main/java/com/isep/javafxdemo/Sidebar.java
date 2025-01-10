package com.isep.javafxdemo;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

public class Sidebar {
    private VBox sidebar;
    private Consumer<javafx.scene.Node> navigationHandler;

    public Sidebar() {
        sidebar = new VBox();
        Button employeButton = createButton("Employe");
        Button projetButton = createButton("Projet");
        Button calenderButton = createButton("Calender");
        Button reportButton = createButton("Report");

        employeButton.setOnAction(e -> navigateTo(new EmployeWindow()));
        projetButton.setOnAction(e -> navigateTo(new ProjetWindow()));
        calenderButton.setOnAction(e -> navigateTo(new CalenderWindow()));
        reportButton.setOnAction(e -> navigateTo(new ReportWindow().getWindow()));

        sidebar.getChildren().addAll(employeButton, projetButton, calenderButton, reportButton);
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setMinWidth(150);
        button.setMinHeight(150);// 设置按钮的最小宽度
        return button;
    }

    public VBox getSidebar() {
        return sidebar;
    }

    public void setNavigationHandler(Consumer<javafx.scene.Node> handler) {
        this.navigationHandler = handler;
    }

    private void navigateTo(javafx.scene.Node window) {
        if (navigationHandler != null) {
            navigationHandler.accept(window);
        }
    }
}