package com.isep.javafxdemo;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

public class Sidebar {
    private VBox sidebar;
    private Consumer<javafx.scene.Node> navigationHandler;

    public Sidebar() {
        sidebar = new VBox();
        Button employeButton = new Button("Employe");
        Button projetButton = new Button("Projet");
        Button calenderButton = new Button("Calender");
        Button reportButton = new Button("Report");

        employeButton.setOnAction(e -> navigateTo(new EmployeWindow().getWindow()));
        projetButton.setOnAction(e -> navigateTo(new ProjetWindow().getWindow()));
        calenderButton.setOnAction(e -> navigateTo(new CalenderWindow()));
        reportButton.setOnAction(e -> navigateTo(new ReportWindow().getWindow()));

        sidebar.getChildren().addAll(employeButton, projetButton, calenderButton, reportButton);
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