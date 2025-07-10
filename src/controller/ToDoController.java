package controller;

import application.PersonalApp;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ToDoTask;
import view.ToDoView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ToDoController {
    private Stage primaryStage;
    private PersonalApp app;
    private ToDoView toDoView;
    private int userId;
    private VBox taskList;

    public ToDoController(Stage primaryStage, PersonalApp app, ToDoView toDoView, int userId) {
        this.primaryStage = primaryStage;
        this.app = app;
        this.toDoView = toDoView;
        this.userId = userId;
    }

    private void initializeTasks() {
        List<ToDoTask> tasks = app.getTasksByUserId(userId);
        if (tasks != null) {
            for (ToDoTask task : tasks) {
                toDoView.addTaskToView(task.getTask(), task.isCompleted(), task.getId());
            }
        } else {
            System.out.println("Failed to fetch tasks for user ID: " + userId);
        }
    }

    public void handleBackButton() {
        app.showDashboardView(primaryStage, userId);
    }

    public void addTask(String task) {
        try (Connection connection = DatabaseHelper.getConnection()) {
            ToDoTask toDoTask = ToDoTask.saveTask(connection, userId, task);
            if (toDoTask != null) {
                toDoView.addTaskToView(task, false, toDoTask.getId());
                updateTaskCountLabel(); // Update task count label after adding task
            } else {
                System.out.println("Failed to save task: " + task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void toggleTaskCompletion(int taskId, boolean isCompleted) {
        try (Connection connection = DatabaseHelper.getConnection()) {
            ToDoTask.updateTaskStatus(connection, taskId, isCompleted);
            updateTaskCountLabel(); // Update task count label after toggling task completion
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getCompletedTaskCount() {
        List<ToDoTask> tasks = app.getTasksByUserId(userId);
        int count = 0;
        if (tasks != null) {
            for (ToDoTask task : tasks) {
                if (task.isCompleted()) {
                    count++;
                }
            }
        }
        return count;
    }

    public void deleteTask(int taskId) {
        try (Connection connection = DatabaseHelper.getConnection()) {
            ToDoTask.deleteTask(connection, taskId);
            toDoView.clearTasksFromView();
            initializeTasks();
            updateTaskCountLabel(); // Update task count label after deleting task
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setTaskList(VBox taskList) {
        this.taskList = taskList;
        initializeTasks(); // Call initializeTasks after taskList is set
    }

    private void updateTaskCountLabel() {
        toDoView.updateTaskCountLabel();
    }
}
