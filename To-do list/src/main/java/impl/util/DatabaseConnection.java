package impl.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class DatabaseConnection {
    private static final Properties PROPERTIES = loadProperties();

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("application.properties")) {

            if (input != null) {
                properties.load(input);
            } else {
                throw new IllegalStateException("Файл конфигурации не найден");
            }
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось загрузить конфигурацию", e);
        }
        return properties;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                PROPERTIES.getProperty("db.url"),
                PROPERTIES.getProperty("db.username"),
                PROPERTIES.getProperty("db.password")
        );
    }
}