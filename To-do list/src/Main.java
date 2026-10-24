import java.util.Scanner;

public class Main {
    private static boolean[] done = new boolean[100];
    private static String[] tasks = new String[100];
    private static Scanner scanner = new Scanner(System.in);
    private static int tasksCounter = 0;

    static void main(String[] args) {
        while (true) {
            menuShow();
            String input = scanner.nextLine();
            int number;
            try {
                number = Integer.parseInt(input);

            } catch (NumberFormatException e) {
                System.out.println("Ошибка ввода! Выберите число, соответствующее существующему пункту меню.");
                continue;
            }
            switch (number) {
                case 1:
                    tasksCounter = tasksAdd(tasksCounter);
                    break;
                case 2:
                    showTasks(tasksCounter);
                    break;
                case 3:
                    tasksCounter = tasksRemove(tasksCounter);
                    break;
                case 4:
                    done(tasksCounter);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Ошибка ввода! Выбранный пункт меню отсутствует");
                    break;

            }
        }
    }

    private static void menuShow() {
        System.out.println("""
                Это планировщик задач. Введите число, чтобы попасть в нужный раздел меню.
                >>> Меню:
                1. Добавить задачу
                2. Показать все задачи
                3. Удалить задачу (по номеру)
                4. Отметить задачу как выполненную
                0. Выход
                
                Выберите пункт меню:""");
    }

    private static void showTasks(int tasksCounter) {
        if (tasksCounter == 0) {
            System.out.println("Список задач пуст");
            return;
        }
        System.out.println("Список задач:");
        for (int i = 0; i < tasksCounter; i++) {
            String status = Main.done[i] ? "[X]" : "[ ]";
            System.out.println((i + 1) + ". " + status + " " + Main.tasks[i]);
        }
    }

    private static int tasksAdd(int tasksCounter) {
        if (tasksCounter >= Main.tasks.length) {
            System.out.println("Список задач переполнен!");
            return tasksCounter;
        }
        System.out.println("Введите описание задачи: ");
        String task = Main.scanner.nextLine();
        Main.tasks[tasksCounter] = task;
        Main.done[tasksCounter] = false;
        System.out.println("Задача добавлена!");
        return tasksCounter + 1;
    }

    private static int tasksRemove(int tasksCounter) {
        System.out.print("Введите номер задачи: ");
        String input = Main.scanner.nextLine();
        try {
            int number = Integer.parseInt(input);
            if (number < 1 || number > tasksCounter) {
                System.out.println("Ошибка ввода! Такой задачи не существует");
                return tasksCounter;
            }
            int index = number - 1;
            System.out.println("Задача \"" + Main.tasks[index] + "\" удалена!");
            for (int i = index; i < tasksCounter - 1; i++) {
                Main.tasks[i] = Main.tasks[i + 1];
                Main.done[i] = Main.done[i + 1];
            }
            Main.tasks[tasksCounter - 1] = null;
            Main.done[tasksCounter - 1] = false;
            tasksCounter--;
        } catch (NumberFormatException e) {
            System.out.println("Ошибка ввода! Выберите число, соответствующее номеру задачи");
        }
        return tasksCounter;
    }

    private static void done(int tasksCounter) {
        System.out.println("Введите номер задачи для отметки: ");
        String input = Main.scanner.nextLine();
        try {
            int number = Integer.parseInt(input);
            if (number < 1 || number > tasksCounter) {
                System.out.println("Ошибка ввода! Задача не существует");
                return;
            }
            int index = number - 1;
            if (Main.done[index]) {
                System.out.println("Задача \"" + Main.tasks[index] + "\" уже была выполнена!");
            } else {
                Main.done[index] = true;
                System.out.println("Задача \"" + Main.tasks[index] + "\" отмечена как выполненная!");
            }

        } catch (NumberFormatException e) {
            System.out.println("Ошибка ввода! Введите число, соответствующее номеру задачи");
        }
    }
}



