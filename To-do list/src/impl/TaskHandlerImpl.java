package impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskHandlerImpl implements TaskHandler {
    private final List<Task> tasks;
    private int idCounter = 1;

    public TaskHandlerImpl() {
        this.tasks = new ArrayList<>();
    }

    @Override
    public Task addTask(String name, String description, Task.Priority priority, LocalDate deadline) {
        Task task = new Task(idCounter, name, description, priority, deadline);
        tasks.add(task);
        idCounter++;
        return task;
    }

    @Override
    public int getTaskCount() {
        return tasks.size();
    }

    @Override
    public Task getTaskAt(int index) {
        return tasks.get(index);
    }

    @Override
    public boolean markTaskDone(int index) {
        if (index < 0 || index >= tasks.size()) {
            return false;
        }
        Task task = tasks.get(index);
        if (tasks.get(index).isDone()) {
            return false;
        }
        task.setDone(true);
        return true;

    }

    @Override
    public boolean removeTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            return false;
        }
        tasks.remove(index);
        return true;
    }

}