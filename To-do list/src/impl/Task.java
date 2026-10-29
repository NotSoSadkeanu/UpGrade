package impl;

import java.time.LocalDate;

public class Task {
    private final int id;
    private String name;
    private String description;
    private Priority priority;
    private LocalDate deadline;
    private boolean done;

    public Task(int id, String name, String description, Priority priority, LocalDate deadline) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public enum Priority {
        LOW,
        MEDIUM,
        HIGH,
        URGENT
    }

}