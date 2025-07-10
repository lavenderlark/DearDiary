package controller;

import application.PersonalApp;
import javafx.stage.Stage;
import view.SignupView;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SignupController {
    private Stage primaryStage;
    private PersonalApp app;
    private SignupView signupView;

    public SignupController(Stage primaryStage, PersonalApp app, SignupView signupView) {
        this.primaryStage = primaryStage;
        this.app = app;
        this.signupView = signupView;
    }

    public boolean handleSignup(String username, String password) {
        try (Connection connection = DatabaseHelper.getConnection()) {
            if (isUsernameTaken(connection, username)) {
                return false;
            }

            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, password);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("User signed up: " + username);
                    app.showLoginView(primaryStage);
                    return true;
                } else {
                    System.out.println("Sign up failed! No rows inserted.");
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isUsernameTaken(Connection connection, String username) throws Exception {
        String query = "SELECT 1 FROM users WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }
}
