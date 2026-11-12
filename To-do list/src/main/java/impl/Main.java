package impl;

import impl.service.DatabaseTaskHandler;
import impl.service.InMemoryTaskHandler;
import impl.service.TaskHandler;
import impl.service.TaskManager;
import impl.service.UserService;
import impl.model.User;
import impl.util.TaskPrinter;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        TaskPrinter.printStorageStrategyChoice();
        String strategy = scanner.nextLine();
        TaskHandler taskHandler;
        switch (strategy) {
            case "1" -> taskHandler = new InMemoryTaskHandler();
            case "2" -> {
                TaskPrinter.printUserName();
                String userName = scanner.nextLine();
                UserService userService = new UserService();
                User user = userService.findUserByName(userName);
                if (user == null) {
                    user = new User(userName);
                    userService.saveUser(user);
                }
                taskHandler = new DatabaseTaskHandler(user);
            }
            default -> {
                TaskPrinter.printInvalidStorageStrategy();
                return;
            }
        }
        TaskManager manager = new TaskManager(taskHandler, scanner);
        manager.start();
    }
}