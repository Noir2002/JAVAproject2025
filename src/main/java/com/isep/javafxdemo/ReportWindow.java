package com.isep.javafxdemo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ReportWindow extends VBox {
    private ListView<Projet> projectListView;
    private VBox detailLayout;
    private Projet selectedProject;

    public ReportWindow() {
        projectListView = new ListView<>();
        detailLayout = new VBox(10);
        detailLayout.setPadding(new Insets(10));

        // 设置项目列表视图
        projectListView.getItems().addAll(Projet.getProjets());
        projectListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Projet project, boolean empty) {
                super.updateItem(project, empty);
                if (empty || project == null) {
                    setText(null);
                } else {
                    setText("ID: " + project.getId() + " - " + project.getNom());
                }
            }
        });

        projectListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                selectedProject = projectListView.getSelectionModel().getSelectedItem();
                if (selectedProject != null) {
                    Reporter reporter = new Reporter();
                    String report = reporter.generateReport(selectedProject);
                    showProjectDetails(report);
                }
            }
        });

        // 创建导出按钮
        Button exportButton = new Button("Exporter");
        exportButton.setOnAction(e -> exportToPDF());

        // 布局设置
        ScrollPane projectScrollPane = new ScrollPane(projectListView);
        projectScrollPane.setFitToWidth(true);
        projectScrollPane.setMinWidth(300); // 设置最小宽度

        ScrollPane detailScrollPane = new ScrollPane(detailLayout);
        detailScrollPane.setFitToWidth(true);
        detailScrollPane.setMinWidth(300); // 设置最小宽度

        HBox mainLayout = new HBox(10, projectScrollPane, detailScrollPane);
        mainLayout.setAlignment(Pos.CENTER_LEFT);

        HBox buttonBox = new HBox(10, exportButton);
        buttonBox.setAlignment(Pos.CENTER);

        this.setSpacing(10);
        this.setPadding(new Insets(10));
        this.getChildren().addAll(new Text("Projects"), mainLayout, buttonBox);
    }

    private void showProjectDetails(String report) {
        detailLayout.getChildren().clear();
/* 
        Label idLabel = new Label("ID: " + project.getId());
        Label nameLabel = new Label("Name: " + project.getNom());
        Label dateLimitLabel = new Label("Date Limit: " + project.getDateLimit());
        Label budgetLabel = new Label("Budget: " + project.getBudget());
        Label realCostLabel = new Label("Real Cost: " + project.getRealCost());
*/
        Label reportLabel = new Label("Report: " + report);
        detailLayout.getChildren().addAll(reportLabel);
    }

    private void exportToPDF() {
        if (selectedProject == null) {
            showAlert("No Project Selected", "Please select a project to export.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

        // 设置默认文件名，例如 "Project_ProjectA.pdf"
        String defaultFileName = "Project_" + selectedProject.getNom().replaceAll("\\s+", "_") + ".pdf";
        fileChooser.setInitialFileName(defaultFileName);
        
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            try (FileOutputStream fos = new FileOutputStream(file)) {
                Document document = new Document();
                PdfWriter.getInstance(document, fos);
                document.open();
                document.add(new Paragraph("Project Details"));
                document.add(new Paragraph("ID: " + selectedProject.getId()));
                document.add(new Paragraph("Name: " + selectedProject.getNom()));
                document.add(new Paragraph("Date Limit: " + selectedProject.getDateLimit()));
                document.add(new Paragraph("Budget: " + selectedProject.getBudget()));
                document.add(new Paragraph("Real Cost: " + selectedProject.getRealCost()));
                document.close();
                showAlert("Export Successful", "Project details have been exported to PDF.");
            } catch (DocumentException | IOException e) {
                showAlert("Export Failed", "An error occurred while exporting the project details.");
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Node getWindow() {
        return this;
    }
}
