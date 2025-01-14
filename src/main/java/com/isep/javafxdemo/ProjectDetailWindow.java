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

public class ProjectDetailWindow {
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

    public ProjectDetailWindow(Projet project, boolean isNewProject) {
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

        Button addMemberButton = new Button("Add Member");
        Button deleteMemberButton = new Button("Delete Member");
        HBox memberButtons = new HBox(10, addMemberButton, deleteMemberButton);

        // 初始化看板
        setupKanbanBoard();

        // 添加任务和删除任务按钮
        Button addTacheButton = new Button("Add Task");
        Button deleteTacheButton = new Button("Delete Task");
        HBox tacheButtons = new HBox(10, addTacheButton, deleteTacheButton);
        tacheButtons.setAlignment(Pos.CENTER);

        // 保存按钮
        Button saveButton = new Button("Save");
        this.saveButtonBox = new HBox(saveButton);
        saveButtonBox.setAlignment(Pos.TOP_RIGHT);

        layout.getChildren().addAll(
            new Label("ID:"), idField,
            new Label("Name:"), nameField,
            new Label("Budget:"), budgetField,
            new Label("Real Cost:"), realCostField,
            new Label("Date Limit:"), dateLimitField,
            new Label("Members:"), membersListView, memberButtons,
            new Label("Kanban Board:"), kanbanBoard, tacheButtons,
            saveButtonBox
        );

        Scene scene = new Scene(layout, 600, 800);
        stage.setScene(scene);

        // 按钮事件绑定
        addMemberButton.setOnAction(e -> addMember());
        deleteMemberButton.setOnAction(e -> deleteMember());
        addTacheButton.setOnAction(e -> addTache());
        deleteTacheButton.setOnAction(e -> deleteTache());
        saveButton.setOnAction(e -> saveChanges());
    }

    private void setupKanbanBoard() {
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

        toDoList.getItems().addAll(Kanban.getTachesAFaire());
        System.out.println("taches a faire : " + Kanban.getTachesAFaire());
        inProgressList.getItems().addAll(Kanban.getTachesEnCours());
        System.out.println("taches en cours : " + Kanban.getTachesEnCours());
        doneList.getItems().addAll(Kanban.getTachesTermine());
        System.out.println("taches termine : " + Kanban.getTachesTermine());

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
                    TacheDetailWindow tacheDetailWindow = new TacheDetailWindow(project, selectedTask, this);
                    tacheDetailWindow.show();
                }
            }
        });
    
        inProgressList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Tache selectedTask = inProgressList.getSelectionModel().getSelectedItem();
                if (selectedTask != null) {
                    TacheDetailWindow tacheDetailWindow = new TacheDetailWindow(project, selectedTask, this);
                    tacheDetailWindow.show();
                }
            }
        });
    
        doneList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Tache selectedTask = doneList.getSelectionModel().getSelectedItem();
                if (selectedTask != null) {
                    TacheDetailWindow tacheDetailWindow = new TacheDetailWindow(project, selectedTask, this);
                    tacheDetailWindow.show();
                }
            }
        });
    }
    

    public void refresh(Projet project) {
        /*
        // 清空当前看板
        kanbanBoard.getChildren().clear();
    
        // 重新设置看板内容
        setupKanbanBoard();
        for(Tache tache : Kanban.getTaches()){
            System.out.println("tache : ");
            System.out.println(tache);
        }

        // 将新看板添加到旧屏幕
        VBox layout = (VBox) stage.getScene().getRoot();
        layout.getChildren().remove(5);
        layout.getChildren().remove(6);
        layout.getChildren().add(kanbanBoard);
        layout.getChildren().add(saveButtonBox); */

        this.stage.close();
        ProjectDetailWindow projectDetailWindow = new ProjectDetailWindow(project, false);
        projectDetailWindow.show();
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

    private void addTache() {
        // 打开 TacheDetailWindow 让用户创建任务
        Tache newTache = new Tache(Kanban.getTaches().size() + 1, "", "", 0, 0, 1, "", "", "");
        TacheDetailWindow tacheDetailWindow = new TacheDetailWindow(project, newTache, this);
        tacheDetailWindow.show();
    
        /*
        // 窗口关闭后检查是否需要添加到项目
        tacheDetailWindow.getStage().setOnHiding(event -> {
            if (newTache.getId() > 0) {
                newTache.addTache(project);
                refreshKanbanBoard();
            }
        }); */
    }
    
    private void deleteTache() {
        // 从看板中获取选中的任务
        ListView<Tache> selectedListView = null;
        for (Node node : kanbanBoard.getChildren()) {
            if (node instanceof ListView) {
                ListView<Tache> listView = (ListView<Tache>) node;
                if (!listView.getSelectionModel().isEmpty()) {
                    selectedListView = listView;
                    break;
                }
            }
        }
    
        if (selectedListView != null) {
            Tache selectedTache = selectedListView.getSelectionModel().getSelectedItem();
            if (selectedTache != null) {
                // 确认删除
                Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
                confirmDialog.setTitle("Delete Task");
                confirmDialog.setHeaderText("Are you sure you want to delete this task?");
                confirmDialog.setContentText("Task: " + selectedTache.getNom());
    
                confirmDialog.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        // 从项目和看板中删除任务
                        selectedTache.deleteTache(selectedTache);
                        refresh(project);
                    }
                });
            }
        } else {
            // 未选择任务时提示
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Task Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a task to delete from the Kanban board.");
            alert.showAndWait();
        }
    }
    

    private void saveChanges() {
        if (isNewProject) {
            project = new Projet(Integer.parseInt(idField.getText()), nameField.getText(), dateLimitField.getText(), Double.parseDouble(budgetField.getText()));
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
        MainApp.getRoot().setCenter(new ProjetWindow());
    }

    public void show() {
        stage.show();
    }
}
