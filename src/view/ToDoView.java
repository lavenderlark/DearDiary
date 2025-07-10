package view;

import application.PersonalApp;
import controller.ToDoController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ToDoView {
    private VBox view;
    private ToDoController controller;
    private VBox taskList;
    private Label taskCountLabel;

    public ToDoView(Stage primaryStage, PersonalApp app, int userId) {
        this.controller = new ToDoController(primaryStage, app, this, userId);
        this.taskList = new VBox(10); // Initialize taskList here
        initializeView(primaryStage);
        controller.setTaskList(taskList); // Ensure taskList is set in the controller after initialization
        updateTaskCountLabel(); // Initialize task count label
    }

    private void initializeView(Stage primaryStage) {
        // Create and configure UI components
        Button backButton = createButton("Back", e -> controller.handleBackButton(), "#e91e63"); // Lighter red for Back
        TextField taskField = new TextField();
        taskField.setPromptText("Enter task");
        taskField.setAlignment(Pos.CENTER); // Center the text field

        Button addButton = createButton("Add", e -> {
            controller.addTask(taskField.getText());
            taskField.clear();
            updateTaskCountLabel(); // Update task count label after adding task
        }, "#9c27b0"); // Purple for Add

        // Initialize task count label
        taskCountLabel = new Label();
        updateTaskCountLabel(); // Initialize task count label

        // Set up the layout
        VBox inputBox = new VBox(10);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.getChildren().addAll(taskField, addButton); // Stack text field and button vertically

        // Create a top box to hold the back button
        HBox topBox = new HBox();
        topBox.setAlignment(Pos.TOP_LEFT);
        topBox.getChildren().add(backButton);

        VBox headerBox = new VBox(10);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.getChildren().addAll(topBox, inputBox, taskCountLabel);

        view = new VBox(20);
        view.setAlignment(Pos.CENTER);
        view.getChildren().addAll(headerBox, taskList);
        view.setPadding(new Insets(20));
        view.setStyle("-fx-background-color: #D6A0D1; -fx-border-color: #ccc; -fx-border-width: 1px; -fx-border-radius: 5px;");
    }

    private Button createButton(String text, javafx.event.EventHandler<javafx.event.ActionEvent> action, String color) {
        Button button = new Button(text);
        button.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 20;", color));
        button.setOnAction(action);
        return button;
    }

    public VBox getView() {
        return view;
    }

    public void addTaskToView(String task, boolean isCompleted, int taskId) {
        HBox taskItem = new HBox(10);
        taskItem.setAlignment(Pos.CENTER_LEFT);

        CheckBox taskCheckBox = new CheckBox(task);
        taskCheckBox.setSelected(isCompleted);
        taskCheckBox.setStyle("-fx-font-size: 14px;");

        taskCheckBox.setOnAction(e -> {
            boolean selected = taskCheckBox.isSelected();
            controller.toggleTaskCompletion(taskId, selected);
            updateTaskCountLabel(); // Update task count label after toggling task completion
        });

        Button deleteButton = createButton("Delete", e -> {
            controller.deleteTask(taskId);
            taskList.getChildren().remove(taskItem);
            updateTaskCountLabel(); // Update task count label after deleting task
        }, "#9c27b0"); // Purple for Delete
        deleteButton.setStyle("-fx-font-size: 14px;");

        taskItem.getChildren().addAll(taskCheckBox, deleteButton);
        taskList.getChildren().add(taskItem);
    }

    public void clearTasksFromView() {
        taskList.getChildren().clear();
        updateTaskCountLabel(); // Clear task count label
    }

    public void updateTaskCountLabel() {
        int completedTasks = controller.getCompletedTaskCount();
        int totalTasks = taskList.getChildren().size();
        taskCountLabel.setText("Tasks completed: " + completedTasks + "/" + totalTasks);
        taskCountLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
    }
}
