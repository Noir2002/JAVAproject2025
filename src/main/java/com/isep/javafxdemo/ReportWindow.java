// 包声明，指定该类属于哪个包
package com.isep.javafxdemo;

// 导入所需的 Java 和 JavaFX 类库
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.geometry.Insets; // 设置节点的内边距
import javafx.geometry.Pos; // 设置布局的对齐方式
import javafx.scene.Node; // JavaFX 的基本节点类
import javafx.scene.control.Alert; // 提示框控件
import javafx.scene.control.Button; // 按钮控件
import javafx.scene.control.Label; // 标签控件
import javafx.scene.control.ListCell; // 列表单元格
import javafx.scene.control.ListView; // 列表视图控件
import javafx.scene.control.ScrollPane; // 滚动面板
import javafx.scene.layout.HBox; // 水平布局
import javafx.scene.layout.VBox; // 垂直布局
import javafx.scene.text.Text; // 文本显示控件
import javafx.stage.FileChooser; // 文件选择器
import javafx.stage.Stage; // JavaFX 窗口类

// ReportWindow 类继承 VBox，用于展示项目的报告生成功能
public class ReportWindow extends VBox {
    private ListView<Projet> projectListView; // 用于显示项目列表的 ListView
    private VBox detailLayout; // 用于展示项目详细信息的布局
    private Projet selectedProject; // 当前选中的项目

    // 构造方法，用于初始化窗口组件
    public ReportWindow() {
        projectListView = new ListView<>(); // 创建项目列表视图
        detailLayout = new VBox(10); // 创建垂直布局，设置子节点之间的间距为 10
        detailLayout.setPadding(new Insets(10)); // 设置内边距为 10

        // 设置项目列表的数据源和显示方式
        projectListView.getItems().addAll(Projet.getProjets()); // 从 Projet 类获取项目列表
        projectListView.setCellFactory(param -> new ListCell<>() { // 自定义列表项显示格式
            @Override
            protected void updateItem(Projet project, boolean empty) {
                super.updateItem(project, empty); // 调用父类方法更新项
                if (empty || project == null) { // 如果列表项为空或项目为 null
                    setText(null); // 不显示文本
                } else {
                    setText("ID: " + project.getId() + " - " + project.getNom()); // 显示项目的 ID 和名称
                }
            }
        });

        // 设置鼠标点击事件
        projectListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // 单击事件
                selectedProject = projectListView.getSelectionModel().getSelectedItem(); // 获取选中的项目
                if (selectedProject != null) {
                    Reporter reporter = new Reporter(); // 创建 Reporter 对象
                    String report = reporter.generateReport(selectedProject); // 生成报告
                    showProjectDetails(report); // 显示项目的详细信息
                }
            }
        });

        // 创建导出按钮
        Button exportButton = new Button("Exporter"); // 创建导出按钮
        exportButton.setOnAction(e -> exportToPDF()); // 设置按钮的点击事件，调用导出 PDF 方法

        // 布局设置
        ScrollPane projectScrollPane = new ScrollPane(projectListView); // 创建滚动面板，包含项目列表
        projectScrollPane.setFitToWidth(true); // 使面板宽度适应内容宽度
        projectScrollPane.setMinWidth(300); // 设置最小宽度

        ScrollPane detailScrollPane = new ScrollPane(detailLayout); // 创建滚动面板，包含详细信息
        detailScrollPane.setFitToWidth(true);
        detailScrollPane.setMinWidth(300); // 设置最小宽度

        HBox mainLayout = new HBox(10, projectScrollPane, detailScrollPane); // 水平布局包含列表和详细信息
        mainLayout.setAlignment(Pos.CENTER_LEFT); // 设置左对齐

        HBox buttonBox = new HBox(10, exportButton); // 创建水平布局包含按钮
        buttonBox.setAlignment(Pos.CENTER); // 设置居中对齐

        this.setSpacing(10); // 设置主布局中子节点的间距为 10
        this.setPadding(new Insets(10)); // 设置主布局的内边距为 10
        this.getChildren().addAll(new Text("Projects"), mainLayout, buttonBox); // 将标题文本、主要布局和按钮添加到窗口
    }

    // 显示项目详细信息的方法
    private void showProjectDetails(String report) {
        detailLayout.getChildren().clear(); // 清空详细信息布局的内容
        Label reportLabel = new Label("Report: " + report); // 创建报告标签
        detailLayout.getChildren().addAll(reportLabel); // 添加报告标签到布局
    }

    // 导出选中项目的详细信息为 PDF 文件
    private void exportToPDF() {
        if (selectedProject == null) { // 如果没有选中任何项目
            showAlert("No Project Selected", "Please select a project to export."); // 弹出提示框
            return;
        }

        FileChooser fileChooser = new FileChooser(); // 创建文件选择器
        fileChooser.setTitle("Save PDF"); // 设置文件选择器的标题
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf")); // 限制文件类型为 PDF

        // 设置默认文件名
        String defaultFileName = "Project_" + selectedProject.getNom().replaceAll("\\s+", "_") + ".pdf";
        fileChooser.setInitialFileName(defaultFileName);

        File file = fileChooser.showSaveDialog(new Stage()); // 显示保存文件对话框
        //怎么样保存文件
        if (file != null) { // 如果用户选择了文件路径
            try (FileOutputStream fos = new FileOutputStream(file)) { // 创建文件输出流
                Document document = new Document(); // 创建 PDF 文档对象
                PdfWriter.getInstance(document, fos); // 创建 PDF 写入器
                document.open(); // 打开文档
                document.add(new Paragraph("Project Details")); // 添加段落内容
                document.add(new Paragraph("ID: " + selectedProject.getId()));
                document.add(new Paragraph("Name: " + selectedProject.getNom()));
                document.add(new Paragraph("Date Limit: " + selectedProject.getDateLimit()));
                document.add(new Paragraph("Budget: " + selectedProject.getBudget()));
                document.add(new Paragraph("Real Cost: " + selectedProject.getRealCost()));
                document.add(new Paragraph("\n")); // 空行

                // 添加成员信息
                document.add(new Paragraph("Membres du projet:"));
                if (selectedProject.getMembresProjet().isEmpty()) {
                    document.add(new Paragraph("No members assigned."));
                } else {
                    for (Employe member : selectedProject.getMembresProjet()) {
                        document.add(new Paragraph("- ID: " + member.getId() + ", Name: " + member.getNom()));
                    }
                }
                document.add(new Paragraph("\n")); // 空行

                // 添加任务信息
                document.add(new Paragraph("Taches du projet:"));
                if (selectedProject.getTaches().isEmpty()) {
                    document.add(new Paragraph("No tasks assigned."));
                } else {
                    for (Tache task : selectedProject.getTaches()) {
                        document.add(new Paragraph("- " + task.toString())); // 假设 Tache 类实现了 toString 方法
                    }
                }



                document.close(); // 关闭文档
                showAlert("Export Successful", "Project details have been exported to PDF."); // 提示导出成功
            } catch (DocumentException | IOException e) { // 捕获异常
                showAlert("Export Failed", "An error occurred while exporting the project details."); // 提示导出失败
            }
        }
    }

    // 显示提示框的方法
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // 创建信息提示框
        alert.setTitle(title); // 设置提示框标题
        alert.setHeaderText(null); // 不设置头部文本
        alert.setContentText(message); // 设置提示内容
        alert.showAndWait(); // 显示提示框并等待用户确认
    }

    // 返回窗口节点的方法
    public Node getWindow() {
        return this;
    }
}
