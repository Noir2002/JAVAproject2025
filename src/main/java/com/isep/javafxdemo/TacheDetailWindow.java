package com.isep.javafxdemo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.ArrayList;

public class TacheDetailWindow {
    private Stage stage;
    private Tache tache;
    private TextField idField;
    private TextField nameField;
    private TextField budgetField;
    private TextField realCostField;
    private TextField dateLimitField;
    private TextField priorityField;
    private TextArea descriptionArea;
    private TextArea commentArea;
    private ComboBox<String> categoryComboBox;
    private ListView<Employe> membersListView;
    private boolean isNewTache;

    public TacheDetailWindow(Projet projet, Tache tache, ProjectDetailWindow projectDetailWindow) {
        this.isNewTache = isNewTache(tache.getId());
        this.tache = tache;
        stage = new Stage();
        stage.setTitle(isNewTache ? "Create Task" : "Edit Task");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        // 基本信息输入框
        idField = new TextField(isNewTache ? String.valueOf(generateUniqueId()) : String.valueOf(tache.getId()));
        nameField = new TextField(isNewTache ? "" : tache.getNom());
        budgetField = new TextField(isNewTache ? "" : String.valueOf(tache.getBudget()));
        realCostField = new TextField(isNewTache ? "" : String.valueOf(tache.getRealCost()));
        dateLimitField = new TextField(isNewTache ? "" : tache.getDateLimit());
        priorityField = new TextField(isNewTache ? "" : String.valueOf(tache.getPriority()));

        // 类别选择
        categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("a faire", "en cours", "termine");
        categoryComboBox.setValue(isNewTache ? "a faire" : tache.getCategory());

        // 描述和评论
        descriptionArea = new TextArea(isNewTache ? "" : tache.getDescriptions());
        descriptionArea.setPromptText("Description");
        commentArea = new TextArea(isNewTache ? "" : tache.getCommentaires());
        commentArea.setPromptText("Comments");

        // 成员列表
        membersListView = new ListView<>();
        if (!isNewTache) {
            membersListView.getItems().addAll(tache.getMembresTache());
        }

        Button addMemberButton = new Button("Add Member");
        Button deleteMemberButton = new Button("Delete Member");
        HBox memberButtons = new HBox(10, addMemberButton, deleteMemberButton);

        // 保存按钮
        Button saveButton = new Button("Save");
        HBox saveButtonBox = new HBox(saveButton);
        saveButtonBox.setAlignment(Pos.TOP_RIGHT);

        layout.getChildren().addAll(
            new Label("ID:"), idField,
            new Label("Name:"), nameField,
            new Label("Budget:"), budgetField,
            new Label("Real Cost:"), realCostField,
            new Label("Date Limit:"), dateLimitField,
            new Label("Priority:"), priorityField,
            new Label("Category:"), categoryComboBox,
            new Label("Description:"), descriptionArea,
            new Label("Comments:"), commentArea,
            new Label("Members:"), membersListView, memberButtons,
            saveButtonBox
        );

        Scene scene = new Scene(layout, 600, 800);
        stage.setScene(scene);

        // 按钮事件绑定
        addMemberButton.setOnAction(e -> addMember());
        deleteMemberButton.setOnAction(e -> deleteMember());
        saveButton.setOnAction(e -> saveChanges());
    }

    private boolean isNewTache(int id) {
        for (Tache existingTache : Kanban.getTaches()) {
            if (existingTache.getId() == id) {
                return false;
            }
        }
        return true;
    }

    private void addMember() {
        Stage addMemberStage = new Stage();
        addMemberStage.setTitle("Add Members");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        ListView<Employe> allEmployeesList = new ListView<>();
        allEmployeesList.getItems().addAll(Employe.getEmployes());
        allEmployeesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            for (Employe selectedEmployee : allEmployeesList.getSelectionModel().getSelectedItems()) {
                if (!membersListView.getItems().contains(selectedEmployee)) {
                    membersListView.getItems().add(selectedEmployee);
                }
            }
            addMemberStage.close();
        });

        layout.getChildren().addAll(new Label("Select Members:"), allEmployeesList, addButton);

        Scene scene = new Scene(layout, 300, 400);
        addMemberStage.setScene(scene);
        addMemberStage.show();
    }

    private void deleteMember() {
        Employe selectedMember = membersListView.getSelectionModel().getSelectedItem();
        if (selectedMember != null) {
            membersListView.getItems().remove(selectedMember);
            System.out.println("Delete Member: " + selectedMember.getNom());
        }
    }

    private void saveChanges() {
        if (isNewTache) {
            System.out.println("Creating new task...");
            System.out.println("categoryComboBox.getValue(): " + categoryComboBox.getValue());
            tache = new Tache(
                Integer.parseInt(idField.getText()),
                nameField.getText(),
                dateLimitField.getText(),
                Double.parseDouble(budgetField.getText()),
                Double.parseDouble(realCostField.getText()),
                Integer.parseInt(priorityField.getText()),
                categoryComboBox.getValue(),
                descriptionArea.getText(),
                commentArea.getText()
            );
        } else {
            System.out.println("Updating task...");
            tache.setId(Integer.parseInt(idField.getText()));
            tache.setNom(nameField.getText());
            tache.setBudget(Double.parseDouble(budgetField.getText()));
            tache.setRealCost(Double.parseDouble(realCostField.getText()));
            tache.setDateLimit(dateLimitField.getText());
            tache.setPriority(Integer.parseInt(priorityField.getText()));
            tache.setCategory(categoryComboBox.getValue());
            tache.setDescriptions(descriptionArea.getText());
            tache.setCommentaires(commentArea.getText());
            tache.getMembresTache().clear();
            tache.getMembresTache().addAll(membersListView.getItems());
        }
        System.out.println("Changes saved for task: " + tache.getNom());
        stage.close();
    }

    private int generateUniqueId() {
        int maxId = 0;
        for (Tache t : Kanban.getTaches()) {
            maxId = Math.max(maxId, t.getId());
        }
        return maxId + 1;
    }

    public Stage getStage() {
        return stage;
    }

    public void show() {
        stage.show();
    }
}
