package view;

import application.PersonalApp;
import controller.DailyNotesController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class DailyNotesView {
    private VBox view;
    private DailyNotesController controller;
    private static final String BUTTON_STYLE = "-fx-background-color: #9c27b0; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 20;";

    public DailyNotesView(Stage primaryStage, PersonalApp app, int userId) {
        controller = new DailyNotesController(primaryStage, app, this, userId);
        initializeView(primaryStage);
        primaryStage.setScene(new Scene(view, 375, 667)); // Set the stage size to resemble a phone screen
        primaryStage.show();
    }

    private void initializeView(Stage primaryStage) {
        // Background Image
        BackgroundImage backgroundImage = new BackgroundImage(
            new Image("file:C:/Users/91730/OneDrive/Desktop/pastelbg.jpg"),
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT
        );
        view = new VBox(20);
        view.setAlignment(Pos.CENTER);
        view.setBackground(new Background(backgroundImage));

        // Buttons
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #e91e63; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 20;");
        backButton.setOnAction(e -> controller.handleBackButton());

        Button makeEntryButton = new Button("Make Entry");
        makeEntryButton.setStyle(BUTTON_STYLE);
        makeEntryButton.setOnAction(e -> showMakeEntryDialog(primaryStage));

        Button viewEntryButton = new Button("View Entry");
        viewEntryButton.setStyle(BUTTON_STYLE);
        viewEntryButton.setOnAction(e -> showViewEntryDialog(primaryStage));

        view.getChildren().addAll(backButton, makeEntryButton, viewEntryButton);
    }

    public VBox getView() {
        return view;
    }

    private void showMakeEntryDialog(Stage primaryStage) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);

        DatePicker datePicker = new DatePicker();
        TextField titleField = new TextField();
        titleField.setPromptText("Title");

        TextArea contentArea = new TextArea();
        contentArea.setPromptText("Content");

        Button saveButton = new Button("Save Entry");
        saveButton.setStyle(BUTTON_STYLE);
        saveButton.setOnAction(e -> {
            controller.saveEntry(datePicker.getValue(), titleField.getText(), contentArea.getText());
            dialog.close();
        });

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #e91e63; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 20;");
        backButton.setOnAction(e -> dialog.close());

        VBox dialogVbox = new VBox(10);
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.getChildren().addAll(new Label("Select Date:"), datePicker, titleField, contentArea, saveButton, backButton);

        Scene dialogScene = new Scene(dialogVbox, 300, 400);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private void showViewEntryDialog(Stage primaryStage) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);

        DatePicker datePicker = new DatePicker();
        Label titleLabel = new Label();
        TextArea contentArea = new TextArea();
        contentArea.setEditable(false);

        Button viewButton = new Button("View Entry");
        viewButton.setStyle(BUTTON_STYLE);
        viewButton.setOnAction(e -> controller.viewEntry(datePicker.getValue()));

        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-background-color: #e91e63; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 20;");
        closeButton.setOnAction(e -> dialog.close());

        VBox dialogVbox = new VBox(10);
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.getChildren().addAll(new Label("Select Date:"), datePicker, viewButton, titleLabel, contentArea, closeButton);

        Scene dialogScene = new Scene(dialogVbox, 300, 400);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    public void displayEntry(String title, String content) {
        for (Window window : Stage.getWindows()) {
            if (window instanceof Stage && ((Stage) window).getModality() == Modality.APPLICATION_MODAL && window.isShowing()) {
                Stage stage = (Stage) window;
                VBox vbox = (VBox) stage.getScene().getRoot();
                Label titleLabel = (Label) vbox.getChildren().get(3);
                TextArea contentArea = (TextArea) vbox.getChildren().get(4);
                titleLabel.setText("Title: " + title);
                contentArea.setText(content);
            }
        }
    }
}
