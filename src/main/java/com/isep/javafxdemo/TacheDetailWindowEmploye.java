package com.isep.javafxdemo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TacheDetailWindowEmploye {
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
    //private boolean isNewTache;
    private Projet projet;

    public TacheDetailWindowEmploye(Projet projet, Tache tache) {
        //this.isNewTache = isNewTache(tache.getId());
        this.tache = tache;
        this.projet = projet;
        stage = new Stage();
        stage.setTitle("Task");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        // 基本信息输入框
        idField = new TextField(String.valueOf(tache.getId()));
        nameField = new TextField(tache.getNom());
        budgetField = new TextField(String.valueOf(tache.getBudget()));
        realCostField = new TextField(String.valueOf(tache.getRealCost()));
        dateLimitField = new TextField(tache.getDateLimit());
        priorityField = new TextField(String.valueOf(tache.getPriority()));

        // 类别选择
        categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("a faire", "en cours", "termine");
        if(tache.getCategory() != "a faire" && tache.getCategory() != "en cours" && tache.getCategory() != "termine"){
            categoryComboBox.setValue("a faire");
        }else{
            categoryComboBox.setValue(tache.getCategory());
        }

        // 描述和评论
        descriptionArea = new TextArea(tache.getDescriptions());
        descriptionArea.setPromptText("Description");
        commentArea = new TextArea(tache.getCommentaires());
        commentArea.setPromptText("Comments");

        // 成员列表
        membersListView = new ListView<>();
        if (tache.getMembresTache() != null) {
            membersListView.getItems().addAll(tache.getMembresTache());
        }

        // 保存按钮
        Button saveButton = new Button("Save");
        HBox saveButtonBox = new HBox(saveButton);
        saveButtonBox.setAlignment(Pos.TOP_RIGHT);

        // editability
        idField.setEditable(false);
        nameField.setEditable(false);
        budgetField.setEditable(false);
        realCostField.setEditable(false);
        dateLimitField.setEditable(false);


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
            new Label("Members:"), membersListView,
            saveButtonBox
        );

        Scene scene = new Scene(layout, 600, 800);
        stage.setScene(scene);

        // 按钮事件绑定
        saveButton.setOnAction(e -> saveChanges());
    }

    private void saveChanges() {       
        if(!projet.getTaches().isEmpty()){
            for (Tache t : projet.getTaches()) {
                if (t.getId() == tache.getId()) {
                    projet.getTaches().remove(t);
                    break;
                }
            }
        }
        System.out.println("categoryComboBox.getValue(): " + categoryComboBox.getValue());
        tache.setId(Integer.parseInt(idField.getText()));
        tache.setNom(nameField.getText());
        tache.setDateLimit(dateLimitField.getText());
        tache.setBudget(Double.parseDouble(budgetField.getText()));
        tache.setRealCost(Double.parseDouble(realCostField.getText()));
        tache.setPriority(Integer.parseInt(priorityField.getText()));
        tache.setCategory(categoryComboBox.getValue());
        tache.setDescriptions(descriptionArea.getText());
        tache.setCommentaires(commentArea.getText());
        tache.getMembresTache().clear();
        tache.getMembresTache().addAll(membersListView.getItems());

        if (!projet.getTaches().contains(tache)) {
            projet.addTache(tache);
        }

        System.out.println("Tache: " + tache + "--------------------》》》 Projet: " + projet.getId());
        stage.close();
        new ProjectDetailWindowEmploye(projet, false).show();
    }

    public Stage getStage() {
        return stage;
    }

    public void show() {
        stage.show();
    }   
}
