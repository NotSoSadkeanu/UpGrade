package impl.service;

import impl.model.User;
import impl.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class UserService {

    public void saveUser(User user) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO users (user_name) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, user.getUserName());
                preparedStatement.executeUpdate();
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getInt(1));
                    }
                }
            }
        }
    }

    public User findUserByName(String userName) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT id, user_name FROM users WHERE user_name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, userName);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        User user = new User(resultSet.getString("user_name"));
                        user.setId(resultSet.getInt("id"));
                        return user;
                    }
                    return null;
                }
            }
        }
    }
}