package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ToDoTask;
import view.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import controller.DatabaseHelper;

public class PersonalApp extends Application {
    private int userId;  // Track the logged-in user

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("DearDiary");

        // Start with the Login View
        showLoginView(primaryStage);
    }

    public void showLoginView(Stage primaryStage) {
        LoginView loginView = new LoginView(primaryStage, this);
        primaryStage.show();
    }

    public void showSignupView(Stage primaryStage) {
        SignupView signupView = new SignupView(primaryStage, this);
        primaryStage.show();
    }

    public void showDashboardView(Stage primaryStage, int userId) {
        this.userId = userId;
        DashboardView dashboardView = new DashboardView(primaryStage, this, userId);
        primaryStage.show();
    }

    public void showDailyNotesView(Stage primaryStage) {
        DailyNotesView dailyNotesView = new DailyNotesView(primaryStage, this, userId);
        primaryStage.show();
    }

    public void showBirthdaysView(Stage primaryStage) {
        BirthdaysView birthdaysView = new BirthdaysView(primaryStage, this, userId);
        primaryStage.show();
    }

    public void showToDoView(Stage primaryStage) {
        ToDoView toDoView = new ToDoView(primaryStage, this, userId);
        Scene scene = new Scene(toDoView.getView(), 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public List<ToDoTask> getTasksByUserId(int userId) {
        try (Connection connection = DatabaseHelper.getConnection()) {
            return ToDoTask.getTasksByUserId(connection, userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Handle error appropriately
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
