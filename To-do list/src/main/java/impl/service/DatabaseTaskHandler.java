package impl.service;

import impl.model.Priority;
import impl.model.Task;
import impl.model.User;
import impl.util.DatabaseConnection;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;

public class DatabaseTaskHandler implements TaskHandler {
    private final User user;


    public DatabaseTaskHandler(User user) {
        this.user = user;
    }

    @Override
    public Task addTask(String name, String description, Priority priority, LocalDate deadline) throws SQLException {
        int generatedId;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT into task (name, description, priority, deadline, user_id) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, description);
                preparedStatement.setString(3, priority.name());
                if (deadline != null) {
                    java.sql.Date sqlDate = java.sql.Date.valueOf(deadline);
                    preparedStatement.setDate(4, sqlDate);
                } else {
                    preparedStatement.setNull(4, Types.DATE);
                }
                preparedStatement.setInt(5, user.getId());
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected != 1) {
                    throw new SQLException("Не удалось сохранить задачу!");
                }
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Не удалось получить идентификатор! Попробуйте ещё раз");
                    }
                }
            }
        }
        return new Task(generatedId, name, description, priority, deadline);
    }

    @Override
    public int getTaskCount() throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT COUNT(*) FROM task WHERE user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, user.getId());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt(1);
                    }
                }
                throw new SQLException("Запрос количества задач не вернул результат.");
            }
        }
    }

    @Override
    public boolean markTaskDone(int taskId) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "UPDATE task SET done = TRUE WHERE id = ? and user_id = ? and done = FALSE;";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, taskId);
                preparedStatement.setInt(2, user.getId());
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected == 1;
            }
        }
    }

    @Override
    public boolean removeTask(int taskId) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM task WHERE id = ? and user_id = ?;";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, taskId);
                preparedStatement.setInt(2, user.getId());
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        }
    }

    @Override
    public List<Task> getTasks() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT id, name, description, priority, deadline, done FROM task WHERE user_id = ? ORDER BY id ASC";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, user.getId());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        String name = resultSet.getString(2);
                        String description = resultSet.getString(3);
                        boolean done = resultSet.getBoolean(6);
                        Priority priority = Priority.valueOf(resultSet.getString(4));
                        java.sql.Date sqlDate = resultSet.getDate("deadline");
                        LocalDate deadline = sqlDate != null
                                ? sqlDate.toLocalDate()
                                : null;
                        Task task = new Task(id, name, description, priority, deadline);
                        task.setDone(done);
                        tasks.add(task);
                    }
                }
            }
        }
        return tasks;
    }
}
