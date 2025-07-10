package view;

import application.PersonalApp;
import controller.LoginController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.geometry.Insets;

public class LoginView {
    private VBox view;
    private LoginController controller;

    private TextField usernameField;
    private PasswordField passwordField;
    private Label errorLabel;

    public LoginView(Stage primaryStage, PersonalApp app) {
        // Set the stage size to resemble a phone screen
        primaryStage.setWidth(375);
        primaryStage.setHeight(667);
        controller = new LoginController(primaryStage, app, this);
        initializeView();

        Scene scene = new Scene(view);
        primaryStage.setScene(scene);
    }

    private void initializeView() {
        // Load and configure the logo image
        Image logoImage = new Image("file:C:/Users/91730/OneDrive/Desktop/Dear Diary.jpeg");
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(100); // Set the logo size for mobile
        logoImageView.setFitHeight(100);
        logoImageView.setPreserveRatio(true);
        logoImageView.setClip(new Circle(50, 50, 50)); // Make the image circular

        // Initialize text fields
        usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-background-radius: 15; -fx-padding: 10; -fx-font-size: 14;");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-background-radius: 15; -fx-padding: 10; -fx-font-size: 14;");

        // Initialize buttons
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #e91e63; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 15;");
        loginButton.setOnAction(e -> handleLogin());

        Button signupButton = new Button("Sign Up");
        signupButton.setStyle("-fx-background-color: #9c27b0; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 15;");
        signupButton.setOnAction(e -> controller.handleSignup());

        // Initialize error label
        errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        // Layout settings
        HBox logoBox = new HBox(logoImageView);
        logoBox.setAlignment(Pos.CENTER);
        logoBox.setPadding(new Insets(10));

        VBox formBox = new VBox(15);
        formBox.setAlignment(Pos.CENTER);
        formBox.getChildren().addAll(usernameField, passwordField, loginButton, signupButton, errorLabel);
        formBox.setPadding(new Insets(10));
        formBox.setStyle("-fx-background-color: rgba(243, 229, 245, 0.8); -fx-background-radius: 15;");

        VBox mainBox = new VBox(15);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.getChildren().addAll(logoBox, formBox);
        mainBox.setPadding(new Insets(30));

        view = new VBox();
        view.getChildren().add(mainBox);

        // Set background image
        Image backgroundImage = new Image("file:C:\\Users\\91730\\OneDrive\\Desktop\\bg deardiary.jpg"); // Set your background image path here
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
                loginButton.setFont(new javafx.scene.text.Font(12));
                signupButton.setFont(new javafx.scene.text.Font(12));
            } else {
                formBox.setSpacing(15);
                loginButton.setFont(new javafx.scene.text.Font(14));
                signupButton.setFont(new javafx.scene.text.Font(14));
            }
        });
    }

    public VBox getView() {
        return view;
    }

    public void showError(String message) {
        errorLabel.setText(message);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        controller.handleLogin(username, password);
    }
}
