package com.isep.javafxdemo;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PermissionView extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 创建网格布局
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        // 添加提示标签
        Label promptLabel = new Label("Quel est votre ID ?");
        grid.add(promptLabel, 0, 0, 2, 1);

        // 添加 ID 输入框
        Label idLabel = new Label("ID:");
        TextField idField = new TextField();
        grid.add(idLabel, 0, 1);
        grid.add(idField, 1, 1);

        // 提交按钮
        Button okButton = new Button("OK");
        grid.add(okButton, 1, 2);

        // 按钮点击事件
        okButton.setOnAction(e -> {
            String inputId = idField.getText();

            if (validatePermission(inputId)) {
                // 验证成功，执行任务修改逻辑
                System.out.println("权限验证成功，可以修改任务！");
                primaryStage.close();
                // 继续后续任务处理逻辑

                /*举例说明
                loginButton.setOnAction(e -> {
                    String username = usernameField.getText();
                    String password = passwordField.getText();

                    if (validateUser(username, password)) {
                        // 登录成功，进入主界面
                        MainApp mainView = new MainApp();
                        try {
                            mainView.start(primaryStage);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                 */

            } else {
                // 验证失败，显示错误信息
                Label errorLabel = new Label("ID 无效或权限不足！");
                errorLabel.setStyle("-fx-text-fill: red;");
                grid.add(errorLabel, 1, 3);
            }
        });

        // 设置场景
        Scene scene = new Scene(grid, 400, 200);
        primaryStage.setTitle("权限验证");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // 权限验证逻辑
    private boolean validatePermission(String inputId) {
        // 示例：只有 ID 为 "12345" 或角色为 "manager" 的用户有权限
        return "12345".equals(inputId) || "manager".equalsIgnoreCase(inputId);
    }

    public static void main(String[] args) {
        launch(args);
    }
}


