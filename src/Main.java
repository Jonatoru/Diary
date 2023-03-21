import service.MyDiary;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)){
            label:
            while (true){
                System.out.println("Выберите пунт меню: ");
                printMenu();
                if (scanner.hasNextInt()){
                    int menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            MyDiary.addTask(scanner);
                            break;
                        case 2:
                            MyDiary.editTask(scanner);
                            break;
                        case 3:
                            MyDiary.deleteTask(scanner);
                            break;
                        case 4:
                            MyDiary.getTasksByDay(scanner);
                            break;
                        case 5:
                            MyDiary.printArchivedTasks();
                            break;
                        case 6:
                            MyDiary.getGroupedByDate();
                            break;
                        case 0:
                            break label;
                    }
                } else {
                    scanner.next();
                    System.out.println("Выберите пункт меню из списка");
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println(""+
                "1. Добавить задачу\n" +
                "2. Редактировать задачу\n" +
                "3. Удалить задачу\n" +
                "4. Список задач на день\n" +
                "5. Списко архивных задач\n" +
                "6. Список сгруппированных по датам задач\n" +
                "0. Выход\n" +
                "");
    }
}