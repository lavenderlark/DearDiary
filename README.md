# DearDiary JavaFX Application

## Overview

DearDiary is a personal diary desktop application built with JavaFX. It allows users to:
- Sign up and log in securely
- Write and view daily notes
- Manage birthdays and search for them by name, date, or month
- Create and track to-do tasks with completion status

## Features

- **User Authentication:** Sign up and log in with a username and password.
- **Dashboard:** Access Daily Notes, Birthdays, and To-Do modules from a central dashboard.
- **Daily Notes:** Create, save, and view daily journal entries.
- **Birthdays:** Add, save, and search for birthdays and special occasions.
- **To-Do List:** Add, complete, and delete tasks; see progress with a task counter.
- **Modern UI:** Mobile-like, responsive design with custom backgrounds and styles.

## Project Structure

```
src/
  application/
    Main.java
    PersonalApp.java
    application.css
  controller/
    BirthdaysController.java
    DailyNotesController.java
    DashboardController.java
    DatabaseHelper.java
    LoginController.java
    SignupController.java
    ToDoController.java
  model/
    Birthday.java
    DailyNote.java
    ToDoTask.java
  view/
    BirthdaysView.java
    DailyNotesView.java
    DashboardView.java
    LoginView.java
    SignupView.java
    ToDoView.java
```

## Database

- Uses Oracle Database (configured in [`controller.DatabaseHelper`](src/controller/DatabaseHelper.java))
- Tables required: `users`, `daily_notes`, `birthdays`, `todo_tasks`
- Example connection string:  
  ```
  jdbc:oracle:thin:@localhost:1521:xe
  ```

## How to Run

1. **Set up the Database:**
   - Ensure Oracle Database is running.
   - Create the required tables (`users`, `daily_notes`, `birthdays`, `todo_tasks`).
   - (Optional) Add the stored procedure `save_task` for to-do tasks.

2. **Configure Database Credentials:**
   - Update `DB_USER` and `DB_PASSWORD` in [`controller.DatabaseHelper`](src/controller/DatabaseHelper.java) if needed.

3. **Build and Run:**
   - Open the project in your IDE (e.g., Eclipse, IntelliJ, VS Code).
   - Ensure JavaFX libraries are included in your build path.
   - Run [`application.Main`](src/application/Main.java) or [`application.PersonalApp`](src/application/PersonalApp.java).

4. **Login/Signup:**
   - On first run, sign up for a new account.
   - Log in to access the dashboard and features.

## Notes

- Background images and logo paths are hardcoded and should be updated to match your local file system or resources.
- JavaFX 20 is used (see `.classpath` and `.settings/org.eclipse.jdt.core.prefs`).
- The application uses a modular structure (`module-info.java`).


## License

This project is for educational purposes.
