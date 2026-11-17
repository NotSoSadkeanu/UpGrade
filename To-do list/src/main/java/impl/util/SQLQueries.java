package impl.util;

public final class SQLQueries {

    public static final String SQL_SELECT_TASKS = "SELECT id, name, description, priority, deadline, done FROM task WHERE user_id = ? ORDER BY id ASC";
    public static final String SQL_COUNT_TASKS = "SELECT COUNT(*) FROM task WHERE user_id = ?";
    public static final String SQL_ADD_TASKS = "INSERT into task (name, description, priority, deadline, user_id) VALUES (?, ?, ?, ?, ?)";
    public static final String SQL_MARK_TASKS = "UPDATE task SET done = TRUE WHERE id = ? and user_id = ? and done = FALSE;";
    public static final String SQL_DELETE_TASKS = "DELETE FROM task WHERE id = ? and user_id = ?;";
    public static final String SQL_INSERT_USER = "INSERT INTO users (user_name) VALUES (?)";
    public static final String SQL_SELECT_USER = "SELECT id, user_name FROM users WHERE user_name = ?";

    private SQLQueries() {

    }
}
