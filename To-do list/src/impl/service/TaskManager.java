package impl.service;

import impl.util.TaskPrinter;
import impl.model.Priority;

import java.time.LocalDate;
import java.util.Scanner;
import java.time.format.DateTimeParseException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class TaskManager {
    private final Scanner scanner = new Scanner(System.in);
    private final TaskHandler taskHandler = new TaskHandlerImpl();

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

        taskHandler.addTask(task, description, priorityEnum, deadline);
        TaskPrinter.printTaskAddition();

    }

    private void showTasks() {
        if (taskHandler.getTaskCount() == 0) {
            TaskPrinter.printEmptyList();
            return;
        }
        TaskPrinter.printTasks();
        IntStream.range(0, taskHandler.getTaskCount())
                .forEach(i -> TaskPrinter.printTask(
                        i + 1,
                        taskHandler.getTaskAt(i)
                ));
    }

    private void tasksRemove() {
        TaskPrinter.printTaskRemoveGreeting();
        String input = scanner.nextLine();
        try {
            int number = Integer.parseInt(input);
            if (number < 1 || number > taskHandler.getTaskCount()) {
                TaskPrinter.printTaskInvalidNumber();
                return;
            }
            int index = number - 1;
            String task = taskHandler.getTaskAt(index).getName();
            if (taskHandler.removeTask(index)) {
                TaskPrinter.printTaskRemoved(task);
            }
        } catch (NumberFormatException e) {
            TaskPrinter.printNumberFormatExceptionError();
        }
    }

    private void done() {
        TaskPrinter.printTaskDoneGreeting();
        String input = scanner.nextLine();
        try {
            int number = Integer.parseInt(input);
            if (number < 1 || number > taskHandler.getTaskCount()) {
                TaskPrinter.printTaskInvalidNumber();
                return;
            }
            int index = number - 1;
            String task = taskHandler.getTaskAt(index).getName();
            if (taskHandler.markTaskDone(index)) {
                TaskPrinter.printTaskDone(task);
                return;
            }
            TaskPrinter.printTaskDoneAlready(task);


        } catch (NumberFormatException e) {
            TaskPrinter.printNumberFormatExceptionError();
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