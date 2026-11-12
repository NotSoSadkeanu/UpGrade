package impl.service;

import impl.model.Task;
import impl.model.Priority;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface TaskHandler {
    Task addTask(String name, String description, Priority priority, LocalDate deadline) throws SQLException;

    int getTaskCount() throws  SQLException;

    boolean markTaskDone(int taskId) throws  SQLException;

    boolean removeTask(int taskId) throws   SQLException;

    List<Task> getTasks() throws SQLException;
}