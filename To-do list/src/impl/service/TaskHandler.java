package impl.service;

import impl.model.Task;
import impl.model.Priority;
import java.time.LocalDate;

public interface TaskHandler {
    Task addTask(String name, String description, Priority priority, LocalDate deadline);

    int getTaskCount();

    Task getTaskAt(int index);

    boolean markTaskDone(int index);

    boolean removeTask(int index);
}