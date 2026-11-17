package impl.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;


@Getter
public class Task {
    private final int id;

    @NonNull
    private String name;

    @NonNull
    @Setter
    private String description;

    @NonNull
    @Setter
    private Priority priority;

    @NonNull
    @Setter
    private LocalDate deadline;

    @Setter
    private boolean done;

    @Builder
    public Task(int id, @NonNull String name, @NonNull String description, @NonNull Priority priority, @NonNull LocalDate deadline) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
    }

}