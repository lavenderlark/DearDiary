package view;

import application.PersonalApp;
import controller.DashboardController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.shape.Circle;

public class DashboardView {
    private BorderPane view;
    private PersonalApp app;
    private int userId;
    private DashboardController controller;

    public DashboardView(Stage primaryStage, PersonalApp app, int userId) {
        this.app = app;
        this.userId = userId;
        this.controller = new DashboardController(primaryStage, app, this);
        initializeView();

        Scene scene = new Scene(view);
        primaryStage.setScene(scene);
        primaryStage.setWidth(375); // Set the stage size to resemble a phone screen
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

        // Initialize buttons
        Button dailyNotesButton = createButton("Daily Notes");
        Button birthdaysButton = createButton("Birthdays");
        Button toDoButton = createButton("To-Do");
        Button logoutButton = createButton("Log Out");
        logoutButton.setStyle("-fx-background-color: #e91e63; -fx-text-fill: white;");

        // Set button actions
        dailyNotesButton.setOnAction(e -> controller.handleDailyNotesButton());
        birthdaysButton.setOnAction(e -> controller.handleBirthdaysButton());
        toDoButton.setOnAction(e -> controller.handleToDoButton());
        logoutButton.setOnAction(e -> controller.handleLogoutButton());

        // Top layout with logo
        HBox topLayout = new HBox(logoImageView);
        topLayout.setAlignment(Pos.TOP_LEFT);
        topLayout.setPadding(new Insets(10));

        // Center layout with the main buttons
        VBox centerLayout = new VBox(15);
        centerLayout.setAlignment(Pos.CENTER);
        centerLayout.getChildren().addAll(dailyNotesButton, birthdaysButton, toDoButton);
        centerLayout.setPadding(new Insets(10));

        // Bottom layout with logout button
        HBox bottomLayout = new HBox(logoutButton);
        bottomLayout.setAlignment(Pos.BOTTOM_RIGHT);
        bottomLayout.setPadding(new Insets(10));
        bottomLayout.setStyle("-fx-background-color: rgba(243, 229, 245, 0.8);");

        // Main layout
        view = new BorderPane();
        view.setTop(topLayout);
        view.setCenter(centerLayout);
        view.setBottom(bottomLayout);

        // Set background image
        Image backgroundImage = new Image("file:C:/Users/91730/OneDrive/Desktop/bg deardiary.jpg");
        BackgroundImage background = new BackgroundImage(
            backgroundImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.DEFAULT,
            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, BackgroundSize.DEFAULT.isContain(), true)
        );
        view.setBackground(new Background(background));

        // Add responsiveness
        view.widthProperty().addListener((observable, oldValue, newValue) -> {
            double width = newValue.doubleValue();
            if (width < 400) {
                centerLayout.setSpacing(10);
                dailyNotesButton.setFont(new javafx.scene.text.Font(12));
                birthdaysButton.setFont(new javafx.scene.text.Font(12));
                toDoButton.setFont(new javafx.scene.text.Font(12));
                logoutButton.setFont(new javafx.scene.text.Font(12));
            } else {
                centerLayout.setSpacing(15);
                dailyNotesButton.setFont(new javafx.scene.text.Font(14));
                birthdaysButton.setFont(new javafx.scene.text.Font(14));
                toDoButton.setFont(new javafx.scene.text.Font(14));
                logoutButton.setFont(new javafx.scene.text.Font(14));
            }
        });
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #9c27b0; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 15;");
        button.setPrefSize(150, 40); // Set preferred size
        return button;
    }

    public BorderPane getView() {
        return view;
    }
}
