package impl.service;

import impl.model.Task;
import impl.util.TaskPrinter;
import impl.model.Priority;

import java.time.LocalDate;
import java.util.Scanner;
import java.time.format.DateTimeParseException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.sql.SQLException;
import java.util.List;

public class TaskManager {
    private final Scanner scanner;
    private final TaskHandler taskHandler;

    public TaskManager() {
        this(new InMemoryTaskHandler(), new Scanner(System.in));
    }

    public TaskManager(TaskHandler taskHandler) {
        this(taskHandler, new Scanner(System.in));
    }

    public TaskManager(TaskHandler taskHandler, Scanner scanner) {
        this.taskHandler = taskHandler;
        this.scanner = scanner;
    }

    public void start() {
        while (true) {
            TaskPrinter.printMenu();
            String input = scanner.nextLine();
            int number;
            try {
                number = Integer.parseInt(input);

            } catch (NumberFormatException e) {
                TaskPrinter.printMenuInputError();
                continue;
            }
            switch (number) {
                case 1:
                    tasksAdd();
                    break;
                case 2:
                    showTasks();
                    break;
                case 3:
                    tasksRemove();
                    break;
                case 4:
                    done();
                    break;
                case 0:
                    return;
                default:
                    TaskPrinter.printDefaultError();
                    break;
            }
        }
    }

    private void tasksAdd() {
        TaskPrinter.printTaskAddGreeting();
        String task = scanner.nextLine();

        TaskPrinter.printTaskPriorityGreeting();
        Priority priorityEnum = readInputFromUser(
                input -> Priority.valueOf(input.toUpperCase()),
                e -> e instanceof IllegalArgumentException,
                TaskPrinter::printTaskPriorityInputError
        );
        TaskPrinter.printTaskDeadlineGreeting();
        LocalDate deadline = readInputFromUser(
                LocalDate::parse,
                e -> e instanceof DateTimeParseException,
                TaskPrinter::printTaskDeadlineInputError
        );

        TaskPrinter.printTaskDescriptionGreeting();
        String description = scanner.nextLine();

        try {
            taskHandler.addTask(task, description, priorityEnum, deadline);
            TaskPrinter.printTaskAddition();
        } catch (SQLException e) {
            TaskPrinter.printTaskSaveDatabaseError();
        }
    }

    private void showTasks() {
        try {
            List<Task> tasks = taskHandler.getTasks();

            if (tasks.isEmpty()) {
                TaskPrinter.printEmptyList();
                return;
            }
            TaskPrinter.printTasks();
            IntStream.range(0, tasks.size())
                    .forEach(i -> TaskPrinter.printTask(
                            i + 1, tasks.get(i)
                    ));
        } catch (SQLException e) {
            TaskPrinter.printTasksLoadDatabaseError();
        }
    }

    private void tasksRemove() {
        TaskPrinter.printTaskRemoveGreeting();
        String input = scanner.nextLine();
        try {
            int number = Integer.parseInt(input);
            List<Task> tasks = taskHandler.getTasks();
            if (number < 1 || number > tasks.size()) {
                TaskPrinter.printTaskInvalidNumber();
                return;
            }
            int index = number - 1;
            Task task = tasks.get(index);
            String taskName = task.getName();
            if (taskHandler.removeTask(task.getId())) {
                TaskPrinter.printTaskRemoved(taskName);
            }
        } catch (NumberFormatException e) {
            TaskPrinter.printNumberFormatExceptionError();
        } catch (SQLException e) {
            TaskPrinter.printTaskRemoveDatabaseError();
        }
    }

    private void done() {
        TaskPrinter.printTaskDoneGreeting();
        String input = scanner.nextLine();
        try {
            int number = Integer.parseInt(input);
            List<Task> tasks = taskHandler.getTasks();
            if (number < 1 || number > tasks.size()) {
                TaskPrinter.printTaskInvalidNumber();
                return;
            }
            int index = number - 1;
            Task task = tasks.get(index);
            String taskName = task.getName();
            if (taskHandler.markTaskDone(task.getId())) {
                TaskPrinter.printTaskDone(taskName);
                return;
            }
            TaskPrinter.printTaskDoneAlready(taskName);
        } catch (NumberFormatException e) {
            TaskPrinter.printNumberFormatExceptionError();
        } catch (SQLException e) {
            TaskPrinter.printTaskDoneDatabaseError();
        }
    }

    private <T> T readInputFromUser(Function<String, T> parser, Predicate<RuntimeException> invalidInput, Runnable errorPrint) {
        while (true) {
            String input = scanner.nextLine();
            try {
                return parser.apply(input);
            } catch (RuntimeException e) {
                if (invalidInput.test(e)) {
                    errorPrint.run();
                    continue;
                }
                throw e;
            }
        }
    }
}