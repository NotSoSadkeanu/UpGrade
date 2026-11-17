package impl.service;

import impl.model.Priority;
import impl.model.Task;
import impl.model.User;
import impl.util.DatabaseConnection;
import impl.util.SQLQueries;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class DatabaseTaskHandler implements TaskHandler {
    private final User user;

    public DatabaseTaskHandler(User user) {
        this.user = user;
    }

    @Override
    public Task addTask(String name, String description, Priority priority, LocalDate deadline) throws SQLException {
        int generatedId;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SQL_ADD_TASKS, Statement.RETURN_GENERATED_KEYS)) {
            bindTaskParameters(preparedStatement, name, description, priority, deadline);
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
        return Task.builder()
                .id(generatedId)
                .name(name)
                .description(description)
                .priority(priority)
                .deadline(deadline)
                .build();
    }

    @Override
    public int getTaskCount() throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SQL_COUNT_TASKS)) {
            preparedStatement.setInt(1, user.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
            throw new SQLException("Запрос количества задач не вернул результат.");
        }
    }

    @Override
    public boolean markTaskDone(int taskId) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SQL_MARK_TASKS)) {
            preparedStatement.setInt(1, taskId);
            preparedStatement.setInt(2, user.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected == 1;
        }
    }

    @Override
    public boolean removeTask(int taskId) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SQL_DELETE_TASKS)) {
            preparedStatement.setInt(1, taskId);
            preparedStatement.setInt(2, user.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    @Override
    public List<Task> getTasks() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SQL_SELECT_TASKS)) {
            preparedStatement.setInt(1, user.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    tasks.add(mapTask(resultSet));
                }
            }
        }

        return tasks;
    }

    private Task mapTask(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        String name = resultSet.getString(2);
        String description = resultSet.getString(3);
        boolean done = resultSet.getBoolean(6);
        Priority priority = Priority.valueOf(resultSet.getString(4));
        java.sql.Date sqlDate = resultSet.getDate("deadline");
        LocalDate deadline = sqlDate.toLocalDate();
        Task task = Task.builder()
                .id(id)
                .name(name)
                .description(description)
                .priority(priority)
                .deadline(deadline)
                .build();
        task.setDone(done);
        return task;
    }

    private void bindTaskParameters(
            PreparedStatement preparedStatement,
            String name,
            String description,
            Priority priority,
            LocalDate deadline
    ) throws SQLException {
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, description);
        preparedStatement.setString(3, priority.name());
        preparedStatement.setDate(4, java.sql.Date.valueOf(deadline));
        preparedStatement.setInt(5, user.getId());

    }
}