package controller;

import application.PersonalApp;
import javafx.stage.Stage;
import view.DashboardView;
import view.LoginView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    private Stage primaryStage;
    private PersonalApp app;
    private LoginView loginView;

    public LoginController(Stage primaryStage, PersonalApp app, LoginView loginView) {
        this.primaryStage = primaryStage;
        this.app = app;
        this.loginView = loginView;
    }

    public void handleLogin(String username, String password) {
        String sql = "SELECT id FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id");
                app.showDashboardView(primaryStage, userId);
            } else {
                loginView.showError("Invalid username or password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            loginView.showError("Database connection error");
        }
    }

    public void handleSignup() {
        // Implement signup logic or navigate to signup view
        app.showSignupView(primaryStage);
    }
}
