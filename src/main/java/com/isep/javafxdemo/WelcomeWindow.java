package com.isep.javafxdemo;

import java.io.IOException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class WelcomeWindow extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        // 设置背景图片
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image("file:D:/IntelliJ IDEA 2024.2.3/ISEP A1 A&P/javafx-demo/Designer.png"), // 替换为你的图片路径
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, false)
        );
        layout.setBackground(new Background(backgroundImage));

        Text welcomeText = new Text("Welcome to Collaborative Task Management Application");
        welcomeText.setFont(Font.font("Georgia", 24)); // 设置字体和大小
        welcomeText.setFill(javafx.scene.paint.Color.RED); // 设置颜色
        welcomeText.setTextAlignment(TextAlignment.CENTER); // 设置文本对齐方式
        Button enterButton = new Button("Enter");
        enterButton.setMinWidth(100); // 设置按钮的最小宽度
        enterButton.setMinHeight(40); // 设置按钮的最小高度
        enterButton.setMaxWidth(200); // 设置按钮的最大宽度
        enterButton.setMaxHeight(50); // 设置按钮的最大高度

        // 设置按钮的位置
        VBox.setMargin(enterButton, new Insets(50, 0, 0, 0)); // 设置按钮的外边距

        enterButton.setOnAction(e -> {
            try {
                DataHandler.loadData();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            PermissionView permissionView = new PermissionView();
            Stage permissionStage = new Stage();
            try {
                permissionView.start(permissionStage);
                primaryStage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        layout.getChildren().addAll(welcomeText, enterButton);

        Scene scene = new Scene(layout, 700, 700);
        primaryStage.setTitle("Collaborative Task Management Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}