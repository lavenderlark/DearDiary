package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ToDoTask {
    private int id;
    private int userId;
    private String task;
    private boolean isCompleted;

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public static ToDoTask saveTask(Connection connection, int userId, String task) throws SQLException {
        String sql = "{ call save_task(?, ?, ?) }";

        try (CallableStatement cstmt = connection.prepareCall(sql)) {
            cstmt.setInt(1, userId);
            cstmt.setString(2, task);
            cstmt.registerOutParameter(3, java.sql.Types.INTEGER);

            cstmt.execute();

            int id = cstmt.getInt(3); // Get the returned id
            ToDoTask toDoTask = new ToDoTask();
            toDoTask.setId(id);
            toDoTask.setUserId(userId);
            toDoTask.setTask(task);
            toDoTask.setCompleted(false);

            return toDoTask;
        }
    }

    public static List<ToDoTask> getTasksByUserId(Connection connection, int userId) throws SQLException {
        List<ToDoTask> tasks = new ArrayList<>();
        String sql = "SELECT id, task, is_completed FROM todo_tasks WHERE user_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ToDoTask task = new ToDoTask();
                task.setId(rs.getInt("id"));
                task.setUserId(userId);
                task.setTask(rs.getString("task"));
                task.setCompleted("Y".equals(rs.getString("is_completed")));
                tasks.add(task);
            }
        }
        return tasks;
    }

    public static void updateTaskStatus(Connection connection, int taskId, boolean isCompleted) throws SQLException {
        String sql = "UPDATE todo_tasks SET is_completed = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, isCompleted ? "Y" : "N");
            pstmt.setInt(2, taskId);
            pstmt.executeUpdate();
        }
    }

    public static void deleteTask(Connection connection, int taskId) throws SQLException {
        String sql = "DELETE FROM todo_tasks WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, taskId);
            pstmt.executeUpdate();
        }
    }
}