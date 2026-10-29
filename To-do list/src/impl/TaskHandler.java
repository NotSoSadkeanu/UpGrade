package impl;

import java.time.LocalDate;

public interface TaskHandler {
    Task addTask(String name, String description, Task.Priority priority, LocalDate deadline);

    int getTaskCount();

    Task getTaskAt(int index);

    boolean markTaskDone(int index);

    boolean removeTask(int index);
}