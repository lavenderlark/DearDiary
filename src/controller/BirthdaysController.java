package controller;
import application.PersonalApp;
import model.Birthday;
import view.BirthdaysView;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
public class BirthdaysController {
   private Stage primaryStage;
   private PersonalApp app;
   private BirthdaysView birthdaysView;
   private List<Birthday> birthdays;
   private int userId;
   private DateTimeFormatter dateFormatter;
   public BirthdaysController(Stage primaryStage, PersonalApp app, BirthdaysView birthdaysView, int userId) {
       this.primaryStage = primaryStage;
       this.app = app;
       this.birthdaysView = birthdaysView;
       this.birthdays = new ArrayList<>();
       this.userId = userId;
       this.dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
       loadBirthdaysFromDB();
   }
   public void handleBackButton() {
       app.showDashboardView(primaryStage, userId);
   }
   public void saveEntry(String name, LocalDate date, String occasion) {
       Birthday birthday = new Birthday(name, date, userId, occasion);
       birthdays.add(birthday);
       saveBirthdayToDB(birthday);
       System.out.println("Saved birthday: " + name + " on " + date + " for " + occasion);
   }
   public void searchEntry(String name, LocalDate date, String month) {
       StringBuilder results = new StringBuilder();
       List<Birthday> filteredBirthdays = new ArrayList<>();
       for (Birthday birthday : birthdays) {
           if (birthday.getUserId() == userId) {
               if (name != null && !name.isEmpty() && birthday.getName().equalsIgnoreCase(name)) {
                   filteredBirthdays.add(birthday);
               } else if (date != null && birthday.getBirthDate().equals(date)) {
                   filteredBirthdays.add(birthday);
               } else if (month != null && !month.isEmpty() && birthday.getBirthDate().getMonth().equals(Month.valueOf(month.toUpperCase()))) {
                   filteredBirthdays.add(birthday);
               }
           }
       }
       filteredBirthdays.sort(Comparator.comparing(Birthday::getBirthDate));
       for (Birthday birthday : filteredBirthdays) {
           results.append("Name: ").append(birthday.getName())
                   .append(", Date: ").append(birthday.getBirthDate().format(dateFormatter))
                   .append(", Occasion: ").append(birthday.getOccasion()).append("\n");
       }
       if (results.length() == 0) {
           results.append("No results found");
       }
       birthdaysView.displaySearchResults(results.toString());
   }
   private void loadBirthdaysFromDB() {
       String sql = "SELECT * FROM birthdays WHERE user_id = ?";
       try (Connection conn = DatabaseHelper.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
           pstmt.setInt(1, userId);
           ResultSet rs = pstmt.executeQuery();
           while (rs.next()) {
               int id = rs.getInt("id");
               String name = rs.getString("name");
               LocalDate birthDate = rs.getDate("birth_date").toLocalDate();
               String occasion = rs.getString("occasion");
               birthdays.add(new Birthday(name, birthDate, userId, occasion));
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }
   }
   private void saveBirthdayToDB(Birthday birthday) {
       String sql = "INSERT INTO birthdays (user_id, name, birth_date, occasion) VALUES (?, ?, ?, ?)";
       try (Connection conn = DatabaseHelper.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
           pstmt.setInt(1, birthday.getUserId());
           pstmt.setString(2, birthday.getName());
           pstmt.setDate(3, java.sql.Date.valueOf(birthday.getBirthDate()));
           pstmt.setString(4, birthday.getOccasion());
           pstmt.executeUpdate();
       } catch (SQLException e) {
           e.printStackTrace();
       }
   }
}
