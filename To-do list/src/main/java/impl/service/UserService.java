package impl.service;

import impl.model.User;
import impl.util.DatabaseConnection;
import impl.util.SQLQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class UserService {

    public void saveUser(User user) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getUserName());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected != 1) {
                throw new SQLException("Не удалось сохранить пользователя!");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Не удалось получить ID пользователя!");
                }
            }
        }
    }

    public User findUserByName(String userName) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SQL_SELECT_USER)) {
            preparedStatement.setString(1, userName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapUser(resultSet);
                }
                return null;
            }
        }
    }

    private User mapUser(ResultSet resultSet) throws SQLException {
        User user = new User(resultSet.getString("user_name"));
        user.setId(resultSet.getInt("id"));
        return user;
    }
}