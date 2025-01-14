package com.isep.javafxdemo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class CalendarWindow extends VBox {
    private YearMonth currentYearMonth;
    private GridPane calendar;
    private Text calendarTitle;
    private Map<LocalDate, Projet> projects;

    public CalendarWindow() {
        currentYearMonth = YearMonth.now();
        projects = new HashMap<>();

        // 将项目添加到日历中
        for (Projet project : Projet.getProjets()) {
            LocalDate deadline = LocalDate.parse(project.getDateLimit().substring(0, 10));
            projects.put(deadline, project);
        }

        // 创建主布局
        this.setSpacing(10);
        this.setPadding(new Insets(10));
        this.setAlignment(Pos.TOP_CENTER);

        // 创建顶部控制栏
        HBox controls = new HBox(10);
        controls.setAlignment(Pos.CENTER);

        Button previousMonth = new Button("◀ Last Month");
        Button nextMonth = new Button("Next Month ▶");
        calendarTitle = new Text();

        controls.getChildren().addAll(previousMonth, calendarTitle, nextMonth);

        // 创建日历网格
        calendar = new GridPane();
        calendar.setHgap(5);
        calendar.setVgap(5);
        calendar.setAlignment(Pos.CENTER);

        // 添加星期标题
        String[] weekDays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (int i = 0; i < 7; i++) {
            Label dayLabel = new Label(weekDays[i]);
            dayLabel.setStyle("-fx-font-weight: bold");
            calendar.add(dayLabel, i, 0);
        }

        // 事件处理
        previousMonth.setOnAction(e -> {
            currentYearMonth = currentYearMonth.minusMonths(1);
            updateCalendar();
        });

        nextMonth.setOnAction(e -> {
            currentYearMonth = currentYearMonth.plusMonths(1);
            updateCalendar();
        });

        this.getChildren().addAll(controls, calendar);

        // 初始化日历显示
        updateCalendar();
    }

    private void updateCalendar() {
        calendarTitle.setText(currentYearMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")));

        // 清除现有日期单元格
        calendar.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);

        // 获取月份信息
        int firstDay = currentYearMonth.atDay(1).getDayOfWeek().getValue() - 1;
        int daysInMonth = currentYearMonth.lengthOfMonth();

        // 填充日期单元格
        int day = 1;
        int row = 1;

        while (day <= daysInMonth) {
            for (int col = 0; col < 7 && day <= daysInMonth; col++) {
                if (row == 1 && col < firstDay) {
                    continue;
                }

                LocalDate date = currentYearMonth.atDay(day);
                VBox dayCell = createDayCell(date, day);
                calendar.add(dayCell, col, row);

                day++;
            }
            row++;
        }
    }

    private VBox createDayCell(LocalDate date, int dayNumber) {
        VBox cell = new VBox(5);
        cell.setPadding(new Insets(5));
        cell.setStyle("-fx-border-color: #cccccc; -fx-background-color: white;");
        cell.setPrefSize(100, 80);

        Label dayLabel = new Label(String.valueOf(dayNumber));
        cell.getChildren().add(dayLabel);

        // 添加当天的项目
        if (projects.containsKey(date)) {
            Projet project = projects.get(date);
            Label projectLabel = new Label(project.getNom());
            projectLabel.setStyle("-fx-text-fill: blue; -fx-font-size: 10px;");
            cell.getChildren().add(projectLabel);

            // 添加点击事件
            cell.setOnMouseClicked(e -> showProjectDetails(project));
        }

        // 如果是今天，高亮显示
        if (date.equals(LocalDate.now())) {
            cell.setStyle(cell.getStyle() + "; -fx-background-color: #e6f3ff;");
        }

        return cell;
    }

    private void showProjectDetails(Projet project) {
        Stage stage = new Stage();
        stage.setTitle("Project Details");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label idLabel = new Label("ID: " + project.getId());
        Label nameLabel = new Label("Name: " + project.getNom());
        Label dateLimitLabel = new Label("Date Limit: " + project.getDateLimit());
        Label budgetLabel = new Label("Budget: " + project.getBudget());
        Label realCostLabel = new Label("Real Cost: " + project.getRealCost());

        layout.getChildren().addAll(idLabel, nameLabel, dateLimitLabel, budgetLabel, realCostLabel);

        Scene scene = new Scene(layout, 300, 200);
        stage.setScene(scene);
        stage.show();
    }
}