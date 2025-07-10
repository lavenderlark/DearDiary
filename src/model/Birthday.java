package model;
import java.time.LocalDate;
public class Birthday {
   private int id;
   private String name;
   private LocalDate birthDate;
   private int userId;
   private String occasion;
   public Birthday() {
   }
   public Birthday(String name, LocalDate birthDate, int userId, String occasion) {
       this.name = name;
       this.birthDate = birthDate;
       this.userId = userId;
       this.occasion = occasion;
   }
   public int getId() {
       return id;
   }
   public void setId(int id) {
       this.id = id;
   }
   public String getName() {
       return name;
   }
   public void setName(String name) {
       this.name = name;
   }
   public LocalDate getBirthDate() {
       return birthDate;
   }
   public void setBirthDate(LocalDate birthDate) {
       this.birthDate = birthDate;
   }
   public int getUserId() {
       return userId;
   }
   public void setUserId(int userId) {
       this.userId = userId;
   }
   public String getOccasion() {
       return occasion;
   }
   public void setOccasion(String occasion) {
       this.occasion = occasion;
   }
}
