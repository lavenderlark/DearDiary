package view;

import application.PersonalApp;
import controller.BirthdaysController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BirthdaysView {
    private VBox view;
    private BirthdaysController controller;
    private DateTimeFormatter dateFormatter;

    public BirthdaysView(Stage primaryStage, PersonalApp app, int userId) {
        dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        controller = new BirthdaysController(primaryStage, app, this, userId);
        initializeView(primaryStage);

        Scene scene = new Scene(view);
        primaryStage.setScene(scene);
        primaryStage.setWidth(375); // Set the stage size to resemble a phone screen
        primaryStage.setHeight(667);
        primaryStage.show();
    }

    private void initializeView(Stage primaryStage) {
        // Load and configure the background image
        Image backgroundImage = new Image("file:C:/Users/91730/OneDrive/Desktop/clouds.jpg");
        BackgroundImage background = new BackgroundImage(
            backgroundImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.DEFAULT,
            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, BackgroundSize.DEFAULT.isContain(), true)
        );

        // Initialize buttons
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #e91e63; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 20;");
        backButton.setOnAction(e -> controller.handleBackButton());

        Button makeEntryButton = new Button("Make Entry");
        makeEntryButton.setStyle("-fx-background-color: #9c27b0; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 20;");
        makeEntryButton.setOnAction(e -> showMakeEntryDialog(primaryStage));

        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: #9c27b0; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 20;");
        searchButton.setOnAction(e -> showSearchDialog(primaryStage));

        view = new VBox(20);
        view.setAlignment(Pos.CENTER);
        view.getChildren().addAll(backButton, makeEntryButton, searchButton);
        view.setBackground(new Background(background));
    }

    public VBox getView() {
        return view;
    }

    private void showMakeEntryDialog(Stage primaryStage) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);

        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        DatePicker datePicker = new DatePicker();
        datePicker.setConverter(new javafx.util.StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return date != null ? date.format(dateFormatter) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return string != null && !string.isEmpty() ? LocalDate.parse(string, dateFormatter) : null;
            }
        });

        TextField occasionField = new TextField();
        occasionField.setPromptText("Occasion");

        Button saveButton = new Button("Save Entry");
        saveButton.setStyle("-fx-background-color: #9c27b0; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 20;");
        saveButton.setOnAction(e -> {
            controller.saveEntry(nameField.getText(), datePicker.getValue(), occasionField.getText());
            dialog.close();
        });

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #e91e63; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 20;");
        backButton.setOnAction(e -> dialog.close());

        VBox dialogVbox = new VBox(10);
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.getChildren().addAll(new Label("Name:"), nameField, new Label("Birth Date:"), datePicker, new Label("Occasion:"), occasionField, saveButton, backButton);

        Scene dialogScene = new Scene(dialogVbox, 300, 400);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private void showSearchDialog(Stage primaryStage) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);

        RadioButton searchByName = new RadioButton("Search by Name");
        RadioButton searchByDate = new RadioButton("Search by Date");
        RadioButton searchByMonth = new RadioButton("Search by Month");

        ToggleGroup searchOptions = new ToggleGroup();
        searchByName.setToggleGroup(searchOptions);
        searchByDate.setToggleGroup(searchOptions);
        searchByMonth.setToggleGroup(searchOptions);

        searchByName.setSelected(true);

        TextField searchField = new TextField();
        searchField.setPromptText("Name");

        DatePicker datePicker = new DatePicker();
        datePicker.setConverter(new javafx.util.StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return date != null ? date.format(dateFormatter) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return string != null && !string.isEmpty() ? LocalDate.parse(string, dateFormatter) : null;
            }
        });
        datePicker.setDisable(true);

        ComboBox<String> monthComboBox = new ComboBox<>();
        monthComboBox.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        monthComboBox.setPromptText("Month");
        monthComboBox.setDisable(true);

        searchByName.setOnAction(e -> {
            searchField.setDisable(false);
            datePicker.setDisable(true);
            monthComboBox.setDisable(true);
        });

        searchByDate.setOnAction(e -> {
            searchField.setDisable(true);
            datePicker.setDisable(false);
            monthComboBox.setDisable(true);
        });

        searchByMonth.setOnAction(e -> {
            searchField.setDisable(true);
            datePicker.setDisable(true);
            monthComboBox.setDisable(false);
        });

        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: #9c27b0; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 20;");
        searchButton.setOnAction(e -> {
            String name = searchField.isDisabled() ? null : searchField.getText();
            LocalDate date = datePicker.isDisabled() ? null : datePicker.getValue();
            String month = monthComboBox.isDisabled() ? null : monthComboBox.getValue();
            controller.searchEntry(name, date, month);
            dialog.close();
        });

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #e91e63; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 20;");
        backButton.setOnAction(e -> dialog.close());

        VBox dialogVbox = new VBox(10);
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.getChildren().addAll(searchByName, searchField, searchByDate, datePicker, searchByMonth, monthComboBox, searchButton, backButton);

        Scene dialogScene = new Scene(dialogVbox, 300, 400);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    public void displaySearchResults(String results) {
        Stage resultStage = new Stage();
        resultStage.initModality(Modality.APPLICATION_MODAL);

        TextArea resultArea = new TextArea(results);
        resultArea.setEditable(false);
        resultArea.setStyle("-fx-font-size: 16px;");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #e91e63; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 20;");
        backButton.setOnAction(e -> resultStage.close());

        VBox resultBox = new VBox(10);
        resultBox.setAlignment(Pos.CENTER);
        resultBox.getChildren().addAll(resultArea, backButton);

        Scene resultScene = new Scene(resultBox, 400, 300);
        resultStage.setScene(resultScene);
        resultStage.show();
    }
}
