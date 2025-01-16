package com.isep.javafxdemo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProjectDetailWindowEmploye {
    private Stage stage;
    private Projet project;
    private TextField idField;
    private TextField nameField;
    private TextField budgetField;
    private TextField realCostField;
    private TextField dateLimitField;
    private ListView<Employe> membersListView;
    private GridPane kanbanBoard;
    private boolean isNewProject;
    private HBox saveButtonBox;

    public ProjectDetailWindowEmploye(Projet project, boolean isNewProject) {
        this.project = project;
        //System.out.println("this projet is : " + project);
        this.isNewProject = isNewProject;
        //System.out.println("isNewProject ? : " + this.isNewProject);
        stage = new Stage();
        stage.setTitle(isNewProject ? "Create Project" : "Edit Project");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        // 基本信息输入框
        idField = new TextField(isNewProject ? String.valueOf(Projet.getProjets().size() + 1) : String.valueOf(project.getId()));
        nameField = new TextField(isNewProject ? "" : project.getNom());
        budgetField = new TextField(isNewProject ? "" : String.valueOf(project.getBudget()));
        realCostField = new TextField(isNewProject ? "" : String.valueOf(project.getRealCost()));
        dateLimitField = new TextField(isNewProject ? "" : project.getDateLimit());

        // 成员列表
        membersListView = new ListView<>();
        if (!isNewProject) {
            membersListView.getItems().addAll(project.getMembresProjet());
        }

        // 初始化看板
        setupKanbanBoard(project);

        // 保存按钮
        Button saveButton = new Button("Save");
        this.saveButtonBox = new HBox(saveButton);
        saveButtonBox.setAlignment(Pos.TOP_RIGHT);

        // 设置不允许修改项目
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
            new Label("Members:"), membersListView,
            new Label("Kanban Board:"), kanbanBoard,
            saveButtonBox
        );

        Scene scene = new Scene(layout, 600, 800);
        stage.setScene(scene);

        // 按钮事件绑定
        saveButton.setOnAction(e -> saveChanges());
    }

    private void setupKanbanBoard(Projet project) {
        Kanban.setKanbanList();
        kanbanBoard = new GridPane();
        kanbanBoard.setHgap(10);
        kanbanBoard.setVgap(10);
        kanbanBoard.setPadding(new Insets(10));

        // 看板列标题
        Label toDoLabel = new Label("À Faire");
        Label inProgressLabel = new Label("En Cours");
        Label doneLabel = new Label("Terminé");

        kanbanBoard.add(toDoLabel, 0, 0);
        kanbanBoard.add(inProgressLabel, 1, 0);
        kanbanBoard.add(doneLabel, 2, 0);

        // 添加任务列表
        ListView<Tache> toDoList = new ListView<>();
        ListView<Tache> inProgressList = new ListView<>();
        ListView<Tache> doneList = new ListView<>();

        if(project.getTaches() != null){
            for(Tache tache : project.getTaches()) {
                for(Tache tache1 : Kanban.getTachesAFaire()) {
                    if(tache.getId() == tache1.getId()) {
                        toDoList.getItems().addAll(Kanban.getTachesAFaire());
                        System.out.println("taches a faire : " + Kanban.getTachesAFaire());
                    }
                }
            }
            
            for(Tache tache : project.getTaches()) {
                for(Tache tache1 : Kanban.getTachesEnCours()) {
                    if(tache.getId() == tache1.getId()) {
                        inProgressList.getItems().addAll(Kanban.getTachesEnCours());
                        System.out.println("taches en cours : " + Kanban.getTachesEnCours());
                    }
                }
            }
            
            for(Tache tache : project.getTaches()) {
                for(Tache tache1 : Kanban.getTachesTermine()) {
                    if(tache.getId() == tache1.getId()) {
                        doneList.getItems().addAll(Kanban.getTachesTermine());
                        System.out.println("taches termine : " + Kanban.getTachesTermine());
                    }
                }
            }
        }
        

        kanbanBoard.add(toDoList, 0, 1);
        kanbanBoard.add(inProgressList, 1, 1);
        kanbanBoard.add(doneList, 2, 1);

        setupMoveTaskButtons(toDoList, inProgressList, doneList);
    }

    private void setupMoveTaskButtons(ListView<Tache> toDoList, ListView<Tache> inProgressList, ListView<Tache> doneList) {
        toDoList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Tache selectedTask = toDoList.getSelectionModel().getSelectedItem();
                if (selectedTask != null) {
                    TacheDetailWindowEmploye tacheDetailWindow = new TacheDetailWindowEmploye(project, selectedTask);
                    tacheDetailWindow.show();
                    this.stage.close();
                }
            }
        });
    
        inProgressList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Tache selectedTask = inProgressList.getSelectionModel().getSelectedItem();
                if (selectedTask != null) {
                    TacheDetailWindowEmploye tacheDetailWindow = new TacheDetailWindowEmploye(project, selectedTask);
                    tacheDetailWindow.show();
                    this.stage.close();
                }
            }
        });
    
        doneList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Tache selectedTask = doneList.getSelectionModel().getSelectedItem();
                if (selectedTask != null) {
                    TacheDetailWindowEmploye tacheDetailWindow = new TacheDetailWindowEmploye(project, selectedTask);
                    tacheDetailWindow.show();
                    this.stage.close();
                }
            }
        });
    }
    

    public void refresh(Projet project) {
        this.stage.close();
        ProjectDetailWindowEmploye projectDetailWindow = new ProjectDetailWindowEmploye(project, false);
        projectDetailWindow.show();
    }

    private void saveChanges() {
        if (isNewProject) {
            project = new Projet(Integer.parseInt(idField.getText()), nameField.getText(), dateLimitField.getText(), Double.parseDouble(budgetField.getText()), Double.parseDouble(realCostField.getText()));
            if(project.getMembresProjet() != null) {
                project.getMembresProjet().clear();
            }
            project.getMembresProjet().addAll(membersListView.getItems());
        } else {
            project.setId(Integer.parseInt(idField.getText()));
            project.setNom(nameField.getText());
            project.setBudget(Double.parseDouble(budgetField.getText()));
            project.setRealCost(Double.parseDouble(realCostField.getText()));
            project.setDateLimit(dateLimitField.getText());
            project.getMembresProjet().clear();
            project.getMembresProjet().addAll(membersListView.getItems());
        }
        System.out.println("Changes saved for project: " + project.getNom());
        stage.close();
        MainAppEmploye.getRoot().setCenter(new ProjetWindowEmploye());
    }

    public Stage getStage() {
        return stage;
    }

    public void show() {
        stage.show();
    }
}
