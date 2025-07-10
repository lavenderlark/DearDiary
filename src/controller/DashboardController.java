package controller;

import application.PersonalApp;
import javafx.stage.Stage;

public class DashboardController {
    private Stage primaryStage;
    private PersonalApp app;
    private view.DashboardView view;

    public DashboardController(Stage primaryStage, PersonalApp app, view.DashboardView view) {
        this.primaryStage = primaryStage;
        this.app = app;
        this.view = view;
    }

    public void handleDailyNotesButton() {
        app.showDailyNotesView(primaryStage);
    }

    public void handleBirthdaysButton() {
        app.showBirthdaysView(primaryStage);
    }

    public void handleToDoButton() {
        app.showToDoView(primaryStage);
    }

    public void handleLogoutButton() {
        app.showLoginView(primaryStage);
    }
}
