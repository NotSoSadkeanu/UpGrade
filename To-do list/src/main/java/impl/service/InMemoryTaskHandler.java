package impl.service;

import impl.model.Task;
import impl.model.Priority;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InMemoryTaskHandler implements TaskHandler {
    private final List<Task> tasks;
    private int idCounter = 1;

    public InMemoryTaskHandler() {
        this.tasks = new ArrayList<>();
    }

    @Override
    public Task addTask(String name, String description, Priority priority, LocalDate deadline) {
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
    public boolean markTaskDone(int taskId) {
        Task task = tasks.stream()
                .filter(t -> t.getId() == taskId)
                .findFirst()
                .orElse(null);

        if (task == null || task.isDone()) {
            return false;
        }

        task.setDone(true);
        return true;
    }

    @Override
    public boolean removeTask(int taskId) {
        Task taskToRemove = tasks.stream()
                .filter(task -> task.getId() == taskId)
                .findFirst()
                .orElse(null);

        return taskToRemove != null && tasks.remove(taskToRemove);
    }

    @Override
    public List<Task> getTasks() {
        return tasks.stream().toList();
    }
}