package impl;

public final class TaskPrinter {
    private TaskPrinter() {
    }

    public static void printTask(int number, Task task) {
        String status = task.isDone() ? " [X]" : " [ ]";
        System.out.println(number + ". " + task.getName() + " " + task.getDescription() + " " + task.getPriority() + " " + task.getDeadline() + status);
    }

    public static void printMenu() {
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

    public static void printTaskAddGreeting() {
        System.out.println("Введите название задачи: ");
    }

    public static void printTaskRemoveGreeting() {
        System.out.println("Введите номер задачи: ");
    }

    public static void printTaskDoneGreeting() {
        System.out.println("Введите номер задачи для отметки: ");
    }

    public static void printTaskInvalidNumber() {
        System.out.println("Ошибка ввода! Такой задачи не существует");
    }

    public static void printNumberFormatExceptionError() {
        System.out.println("Ошибка ввода! Выберите число, соответствующее номеру задачи");
    }


    public static void printEmptyList() {
        System.out.println("Список задач пуст");
    }

    public static void printTasks() {
        System.out.println("Список задач: ");
    }

    public static void printTaskAddition() {
        System.out.println("Задача добавлена!");
    }

    public static void printTaskDone(String task) {
        System.out.println("Задача \"" + task + "\" отмечена как выполненная!");
    }

    public static void printTaskDoneAlready(String task) {
        System.out.println("Задача \"" + task + "\" уже была выполнена!");

    }

    public static void printTaskRemoved(String task) {
        System.out.println("Задача \"" + task + "\" удалена!");
    }

    public static void printDefaultError() {
        System.out.println("Ошибка ввода! Выбранный пункт меню отсутствует");
    }

    public static void printMenuInputError() {
        System.out.println("Ошибка ввода! Выберите число, соответствующее существующему пункту меню.");
    }

    public static void printTaskPriorityGreeting() {
        System.out.println("Выберите приоритет: LOW, MEDIUM, HIGH, URGENT (нужно ввести название приоритета в строке ниже)");
    }

    public static void printTaskPriorityInputError() {
        System.out.println("Ошибка ввода! Выберите один из существующих приоритетов (LOW, MEDIUM, HIGH, URGENT)");
    }

    public static void printTaskDeadlineGreeting() {
        System.out.println("Введите дедлайн в формате ГГГГ-ММ-ДД (например: 2027-01-01):");
    }

    public static void printTaskDeadlineInputError() {
        System.out.println("Ошибка ввода! Введите дедлайн в правильном формате ГГГГ-ММ-ДД (например: 2027-01-01):");
    }

    public static void printTaskDescriptionGreeting() {
        System.out.println("Введите описание задачи: ");
    }

}
