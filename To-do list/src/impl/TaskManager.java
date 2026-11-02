package impl;

import java.time.LocalDate;
import java.util.Scanner;
import java.time.format.DateTimeParseException;

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
        Task.Priority priorityEnum;
        while (true) {
            String priority = scanner.nextLine();
            try {
                priorityEnum = Task.Priority.valueOf(priority.toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                TaskPrinter.printTaskPriorityInputError();
            }
        }
        TaskPrinter.printTaskDeadlineGreeting();
        String deadlineInput;
        LocalDate deadline;
        while (true) {
            deadlineInput = scanner.nextLine();
            try {
                deadline = LocalDate.parse(deadlineInput);
                break;
            } catch (DateTimeParseException e) {
                TaskPrinter.printTaskDeadlineInputError();
            }
        }
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
        for (int i = 0; i < taskHandler.getTaskCount(); i++) {
            Task task = taskHandler.getTaskAt(i);
            TaskPrinter.printTask(i + 1, task);
        }
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

}