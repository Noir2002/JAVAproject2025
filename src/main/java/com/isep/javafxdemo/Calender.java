package com.isep.javafxdemo;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Calender extends Application {
    private YearMonth currentYearMonth;
    private GridPane calender;
    private Text calenderTitle;
    private Map<LocalDate, String> events;
    private VBox mainLayout;


    @Override
    public void start(Stage primaryStage) {
        currentYearMonth = YearMonth.now();
        events = new HashMap<>();

        // 创建主布局
        mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));
        mainLayout.setAlignment(Pos.TOP_CENTER);

        // 创建顶部控制栏
        HBox controls = new HBox(10);
        controls.setAlignment(Pos.CENTER);

        Button previousMonth = new Button("◀ Last Month");
        Button nextMonth = new Button("Next Month ▶");
        calenderTitle = new Text();

        ComboBox<String> courseFilter = new ComboBox<>();
        courseFilter.setPromptText("All class");

        Button newEvent = new Button("new event");

        controls.getChildren().addAll(previousMonth, calenderTitle, nextMonth, courseFilter, newEvent);

        // 创建日历网格
        calender = new GridPane();
        calender.setHgap(5);
        calender.setVgap(5);
        calender.setAlignment(Pos.CENTER);

        // 添加星期标题
        String[] weekDays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (int i = 0; i < 7; i++) {
            Label dayLabel = new Label(weekDays[i]);
            dayLabel.setStyle("-fx-font-weight: bold");
            calender.add(dayLabel, i, 0);
        }

        // 事件处理
        previousMonth.setOnAction(e -> {
            currentYearMonth = currentYearMonth.minusMonths(1);
            updateCalender();
        });

        nextMonth.setOnAction(e -> {
            currentYearMonth = currentYearMonth.plusMonths(1);
            updateCalender();
        });

        newEvent.setOnAction(e -> showNewEventDialog());

        mainLayout.getChildren().addAll(controls, calender);

        // 初始化日历显示
        updateCalender();

        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setTitle("Calender");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateCalender() {
        calenderTitle.setText(currentYearMonth.format(DateTimeFormatter.ofPattern("yyyy年MM月")));

        // 清除现有日期单元格
        calender.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);

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
                calender.add(dayCell, col, row);

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

        // 添加当天的事件
        if (events.containsKey(date)) {
            Label eventLabel = new Label(events.get(date));
            eventLabel.setStyle("-fx-text-fill: blue; -fx-font-size: 10px;");
            cell.getChildren().add(eventLabel);
        }

        // 如果是今天，高亮显示
        if (date.equals(LocalDate.now())) {
            cell.setStyle(cell.getStyle() + "; -fx-background-color: #e6f3ff;");
        }

        // 添加点击事件
        cell.setOnMouseClicked(e -> showEventDialog(date));

        return cell;
    }

    private void showNewEventDialog() {
        // 创建新事件的对话框
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("create new event");

        // 对话框内容
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        DatePicker datePicker = new DatePicker();
        TextArea eventDescription = new TextArea();
        eventDescription.setPrefRowCount(3);

        grid.add(new Label("date:"), 0, 0);
        grid.add(datePicker, 1, 0);
        grid.add(new Label("event description:"), 0, 1);
        grid.add(eventDescription, 1, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK.getText() && datePicker.getValue() != null) {
                events.put(datePicker.getValue(), eventDescription.getText());
                updateCalender();
            }
        });
    }

    private void showEventDialog(LocalDate date) {
        // 显示/编辑特定日期事件的对话框
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " 的事件");

        TextArea eventText = new TextArea(events.getOrDefault(date, ""));
        eventText.setPrefRowCount(3);

        dialog.getDialogPane().setContent(eventText);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK.getText()) {
                if (!eventText.getText().isEmpty()) {
                    events.put(date, eventText.getText());
                } else {
                    events.remove(date);
                }
                updateCalender();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
