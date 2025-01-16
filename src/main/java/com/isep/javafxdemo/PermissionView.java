package com.isep.javafxdemo;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class PermissionView extends Application {

    // 模拟用户数据
    private static final Map<String, String> USERS = new HashMap<>();
    //Employe sample = new Employe(114514, "sunxiaochuan", "manager");

    static {
        USERS.put("12345", "manager"); // 管理员
        USERS.put("54321", "employe"); // 普通员工
    }

    @Override
    public void start(Stage primaryStage) {
        // 创建登录界面的网格布局
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        // 添加提示标签
        Label promptLabel = new Label("Please enter your ID:");
        grid.add(promptLabel, 0, 0, 2, 1);

        // 添加 ID 输入框
        Label idLabel = new Label("ID：");
        TextField idField = new TextField();
        grid.add(idLabel, 0, 1);
        grid.add(idField, 1, 1);

        // 提交按钮
        Button okButton = new Button("Log in");
        grid.add(okButton, 1, 2);

        // 按钮点击事件
        okButton.setOnAction(e -> {
            String inputId = idField.getText();
            //String role = validatePermission(inputId);
            for(Employe em : Employe.getEmployes()){
                if (em.getId() ==  Integer.parseInt(inputId)){
                    if(em.getRole().equals("manager")){
                        System.out.println("The administrator logs in successfully and loads the main interface");
                        openMainApp(primaryStage);
                    }else if(em.getRole().equals("employe")){
                        System.out.println("The visitor logs in successfully and loads the main interface");
                        // open visitor window
                        openMainAppEmploye(primaryStage);
                    }else{
                        // 登录失败，显示错误提示
                        showAccessDenied(primaryStage);
                    }
                    break;
                }else{// 登录失败，显示错误提示
                    Label errorLabel = new Label("Invalid ID!");
                    errorLabel.setStyle("-fx-text-fill: red;");
                    grid.add(errorLabel, 1, 3);
                }
            }
        });

        // 设置场景
        Scene scene = new Scene(grid, 400, 200);
        primaryStage.setTitle("invalid ID!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * 权限验证逻辑
     *
     * @param inputId 用户输入的 ID
     * @return 返回角色类型 ("manager" 或 "employe")，如果验证失败返回 null
     */
    /*private String validatePermission(String inputId) {
        return USERS.getOrDefault(inputId, null);
    }*/

    /**
     * 加载主界面 (MainApp)
     *
     * @param primaryStage 主舞台
     */
    private void openMainApp(Stage primaryStage) {
        MainApp mainApp = new MainApp();
        try {
            mainApp.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        /**
     * 加载主界面 (MainApp)
     *
     * @param primaryStage 主舞台
     */
    private void openMainAppEmploye(Stage primaryStage) {
        MainAppEmploye mainApp = new MainAppEmploye();
        try {
            mainApp.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示权限不足的提示界面
     *
     * @param primaryStage 主舞台
     */
    private void showAccessDenied(Stage primaryStage) {
        Label accessDeniedLabel = new Label("You do not have permission to access this page!");
        accessDeniedLabel.setStyle("-fx-text-fill: red;");
        Button backButton = new Button("返回");
        backButton.setOnAction(e -> start(primaryStage));

        GridPane deniedLayout = new GridPane();
        deniedLayout.setAlignment(Pos.CENTER);
        deniedLayout.setHgap(10);
        deniedLayout.setVgap(10);
        deniedLayout.add(accessDeniedLabel, 0, 0);
        deniedLayout.add(backButton, 0, 1);

        Scene deniedScene = new Scene(deniedLayout, 400, 200);
        primaryStage.setScene(deniedScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
