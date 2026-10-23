import java.util.Scanner;

public class Main {
    static void main(String[] args) {
        boolean[] done = new boolean[100];
        String[] tasks = new String[100];
        Scanner scanner = new Scanner(System.in);
        int tasksCounter = 0;
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
                    tasksCounter = tasksAdd(tasks, tasksCounter, done, scanner);
                    break;
                case 2:
                    showTasks(tasks, tasksCounter, done);
                    break;
                case 3:
                    tasksCounter = tasksRemove(scanner, tasksCounter, done, tasks);
                    break;
                case 4:
                    done(tasks, tasksCounter, done, scanner);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Ошибка ввода! Выбранный пункт меню отсутствует");
                    break;

            }
        }
    }

    static void menuShow() {
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

    static void showTasks(String[] tasks, int tasksCounter, boolean[] done) {
        if (tasksCounter == 0) {
            System.out.println("Список задач пуст");
        } else {
            for (int i = 0; i < tasksCounter; i++) {
                String status = done[i] ? "[X]" : "[ ]";
                System.out.println((i + 1) + ". " + status + " " + tasks[i]);
            }
        }
    }

    static int tasksAdd(String[] tasks, int tasksCounter, boolean[] done, Scanner scanner) {
        if (tasksCounter >= tasks.length) {
            System.out.println("Список задач переполнен!");
            return tasksCounter;
        } else {
            System.out.println("Введите описание задачи: ");
            String task = scanner.nextLine();
            tasks[tasksCounter] = task;
            done[tasksCounter] = false;
            System.out.println("Задача добавлена!");
            return tasksCounter + 1;
        }
    }

    static int tasksRemove(Scanner scanner, int tasksCounter, boolean[] done, String[] tasks) {
        System.out.print("Введите номер задачи: ");
        String input = scanner.nextLine();
        try {
            int number = Integer.parseInt(input);
            if (number < 1 || number > tasksCounter) {
                System.out.println("Ошибка ввода! Такой задачи не существует");
                return tasksCounter;
            }
            int index = number - 1;
            System.out.println("Задача \"" + tasks[index] + "\" удалена!");
            for (int i = index; i < tasksCounter - 1; i++) {
                tasks[i] = tasks[i + 1];
                done[i] = done[i + 1];
            }
            tasks[tasksCounter - 1] = null;
            done[tasksCounter - 1] = false;
            tasksCounter--;
        } catch (NumberFormatException e) {
            System.out.println("Ошибка ввода! Выберите число, соттветствующее номеру задачи");
        }
        return tasksCounter;
    }

    static void done(String[] tasks, int tasksCounter, boolean[] done, Scanner scanner) {
        System.out.println("Введите номер задачи для отметки: ");
        String input = scanner.nextLine();
        try {
            int number = Integer.parseInt(input);
            if (number < 1 || number > tasksCounter) {
                System.out.println("Ошибка ввода! Задача не существует");
                return;
            }
            int index = number - 1;
            if (done[index]) {
            System.out.println("Задача \"" + tasks[index] + "\" уже была выполнена!");
            } else {
            done[index] = true;
            System.out.println("Задача \"" + tasks[index] + "\" отмечена как выполненная!");
        }

    } catch (NumberFormatException e) {
        System.out.println("Ошибка ввода! Введите число, соответствующее номеру задачи");
        }
    }
}



