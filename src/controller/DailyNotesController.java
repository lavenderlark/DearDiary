package controller;

import application.PersonalApp;
import model.DailyNote;
import javafx.stage.Stage;
import view.DailyNotesView;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DailyNotesController {
    private Stage primaryStage;
    private PersonalApp app;
    private DailyNotesView dailyNotesView;
    private List<DailyNote> notes;
    private int userId;

    public DailyNotesController(Stage primaryStage, PersonalApp app, DailyNotesView dailyNotesView, int userId) {
        this.primaryStage = primaryStage;
        this.app = app;
        this.dailyNotesView = dailyNotesView;
        this.notes = new ArrayList<>();
        this.userId = userId;
        loadNotesFromDatabase();
    }

    private void loadNotesFromDatabase() {
        String sql = "SELECT id, note_date, title, content FROM daily_notes WHERE user_id = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                DailyNote note = new DailyNote();
                note.setNoteId(rs.getInt("id"));
                note.setNoteDate(rs.getDate("note_date").toLocalDate());
                note.setTitle(rs.getString("title"));
                note.setContent(rs.getString("content"));
                note.setUserId(userId);
                notes.add(note);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void handleBackButton() {
        app.showDashboardView(primaryStage, userId);
    }

    public void saveEntry(LocalDate noteDate, String title, String content) {
        DailyNote note = new DailyNote();
        note.setNoteDate(noteDate);
        note.setTitle(title);
        note.setContent(content);
        note.setUserId(userId);

        String sql = "INSERT INTO daily_notes (user_id, note_date, title, content) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, new String[]{"ID"})) {

            pstmt.setInt(1, userId);
            pstmt.setDate(2, java.sql.Date.valueOf(noteDate));
            pstmt.setString(3, title);
            pstmt.setString(4, content);
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    note.setNoteId(generatedKeys.getInt(1));
                }
            }
            notes.add(note);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void viewEntry(LocalDate noteDate) {
        for (DailyNote note : notes) {
            if (note.getNoteDate().equals(noteDate)) {
                dailyNotesView.displayEntry(note.getTitle(), note.getContent());
                return;
            }
        }
        dailyNotesView.displayEntry("No entry", "");
    }
}
