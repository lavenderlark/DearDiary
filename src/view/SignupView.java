package view;

import application.PersonalApp;
import controller.SignupController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.geometry.Insets;

public class SignupView {
    private VBox view;
    private SignupController controller;
    private Stage primaryStage;
    private PersonalApp app;

    public SignupView(Stage primaryStage, PersonalApp app) {
        this.primaryStage = primaryStage;
        this.app = app;
        controller = new SignupController(primaryStage, app, this);
        initializeView();

        Scene scene = new Scene(view);
        primaryStage.setScene(scene);
        primaryStage.setWidth(375);  // Set the stage size to resemble a phone screen
        primaryStage.setHeight(667);
        primaryStage.show();
    }

    private void initializeView() {
        // Load and configure the logo image
        Image logoImage = new Image("file:C:/Users/91730/OneDrive/Desktop/Dear Diary.jpeg");
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(100);
        logoImageView.setFitHeight(100);
        logoImageView.setPreserveRatio(true);
        logoImageView.setClip(new Circle(50, 50, 50));

        // Initialize text fields
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-background-radius: 15; -fx-padding: 10; -fx-font-size: 14;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-background-radius: 15; -fx-padding: 10; -fx-font-size: 14;");

        // Initialize buttons
        Button signupButton = new Button("Sign Up");
        signupButton.setStyle("-fx-background-color: #9c27b0; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 15;");
        signupButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                showAlert(AlertType.ERROR, "Form Error!", "Please enter a username and password");
                return;
            }

            boolean success = controller.handleSignup(username, password);
            if (!success) {
                showAlert(AlertType.ERROR, "Signup Error", "Username already taken. Please choose another one.");
            }
        });

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #e91e63; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 15;");
        backButton.setOnAction(e -> app.showLoginView(primaryStage));

        // Layout settings
        HBox logoBox = new HBox(logoImageView);
        logoBox.setAlignment(Pos.CENTER);
        logoBox.setPadding(new Insets(10));

        VBox formBox = new VBox(15);
        formBox.setAlignment(Pos.CENTER);
        formBox.getChildren().addAll(usernameField, passwordField, signupButton, backButton);
        formBox.setPadding(new Insets(10));
        formBox.setStyle("-fx-background-color: rgba(243, 229, 245, 0.8); -fx-background-radius: 15;");

        VBox mainBox = new VBox(15);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.getChildren().addAll(logoBox, formBox);
        mainBox.setPadding(new Insets(30));

        view = new VBox();
        view.getChildren().add(mainBox);

        // Set background image
        Image backgroundImage = new Image("file:C:/Users/91730/OneDrive/Desktop/bg deardiary.jpg");
        BackgroundImage background = new BackgroundImage(
            backgroundImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.DEFAULT,
            new BackgroundSize(BackgroundSize.DEFAULT.getWidth(), BackgroundSize.DEFAULT.getHeight(), true, true, BackgroundSize.DEFAULT.isContain(), true)
        );
        view.setBackground(new Background(background));

        // Add responsiveness
        view.widthProperty().addListener((observable, oldValue, newValue) -> {
            double width = newValue.doubleValue();
            if (width < 400) {
                formBox.setSpacing(10);
                signupButton.setFont(new javafx.scene.text.Font(12));
                backButton.setFont(new javafx.scene.text.Font(12));
            } else {
                formBox.setSpacing(15);
                signupButton.setFont(new javafx.scene.text.Font(14));
                backButton.setFont(new javafx.scene.text.Font(14));
            }
        });
    }

    public VBox getView() {
        return view;
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
