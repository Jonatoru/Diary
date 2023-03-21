package service;

import checks.ValidationUtilities;
import exceptions.TaskNotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class MyDiary {
    private  static  final  Map<Integer, Repeatable> actualTasks = new HashMap<>();
    private  static  final Map<Integer, Repeatable> archivedTasks = new HashMap<>();

    public static void addTask(Scanner scanner) {

        try {
            scanner.nextLine();
            System.out.println("Введите название задачи: ");
            String title = ValidationUtilities.validateString (scanner.nextLine());
            System.out.println("Введите описание задачи: ");
            String description = ValidationUtilities.validateString(scanner.nextLine());
            System.out.println("Введите тип задачи: 0 - Рабочая или 1 - Личная");
            TaskType taskType = TaskType.values()[Integer.parseInt(scanner.nextLine())];
            System.out.println("Введите повторяемость задачи: 0 - Однократная, 1 - Ежедневная, 2 - Еженедельная 3 - Ежемесячная, 4 - Ежегодная");
            int date = scanner.nextInt();
            System.out.println("Введите дату dd.MM.yyyy HH:mm ");
            scanner.nextLine();
            createEvent(scanner, title, description, taskType, date);
            System.out.println("Для вывода нажмите Enter\n");
            scanner.nextLine();
        }catch (TaskNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createEvent (Scanner scanner, String title, String description, TaskType taskType, int date) {

        try {
            LocalDateTime eventDate = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            Repeatable task = null;
            try {
                task = createTask(date, title, description, taskType, eventDate);
                System.out.println("Создана задача " + task);
            } catch (TaskNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }catch (DateTimeParseException e) {
            System.out.println("Проверьте формат dd.MM.yyyy HH:mm и попробуйте ещё раз");
            createEvent(scanner, title, description, taskType, date);
        }
    }

    public static void  editTask (Scanner scanner){
        try {
            System.out.println("Редактирование задачи.\n"+ "Для редактирования введите Id из списка.");
            printActualTasks();
            int id = scanner.nextInt();
            if (!actualTasks.containsKey(id)){
                throw new TaskNotFoundException("Задача не найдена");
            }
            System.out.println("Редактирование\n"+ "Выберите, что хотите изменить: \n"+"0 - заголовок, 1 - описание, 2 - тип, 3 - дата");
            int  menuCase = scanner.nextInt();
            switch (menuCase) {
                case 0 -> {
                    scanner.nextLine();
                    System.out.println("Введите название задачи: ");
                    String title = scanner.nextLine();
                    Task task = (Task)actualTasks.get(id);
                    task.setTitle(title);
                }
                case 1 -> {
                    scanner.nextLine();
                    System.out.println("Введите описание задачи: ");
                    String description = scanner.nextLine();
                    Repeatable task = actualTasks.get(id);
                    task.setDescription(description);
                }
                case 2 -> {
                    scanner.nextLine();
                    System.out.println("Введите тип задачи: 0 - Рабочая или 1 - Личная");
                    TaskType taskType = TaskType.values()[Integer.parseInt(scanner.nextLine())];
                    Repeatable task = actualTasks.get(id);
                    task.setTaskType(taskType);
                }
                case 3 -> {
                    scanner.nextLine();
                    System.out.println("Введите дату dd.MM.yyyy HH:mm ");
                    LocalDateTime localDateTime = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
                    Repeatable task = actualTasks.get(id);
                    task.setFirstDate(localDateTime);
                }
            }
        } catch (TaskNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteTask(Scanner scanner){
        System.out.println("Имеются следующие текущие задачи \n");
        printActualTasks();
        try {
            System.out.println("Для удаления введите Id задачи \n");
            int id = scanner.nextInt();
            if (actualTasks.containsKey(id)) {
                Repeatable removedTask = actualTasks.get(id);
                removedTask.setArchived(true);
                archivedTasks.put(id, removedTask);
                System.out.println("Задача " + id + " удалена\n");
            } else {
                throw new TaskNotFoundException();
            }
        } catch (TaskNotFoundException e) {
            e.printStackTrace();
            System.out.println("Такой задачи не существует\n");
        }
    }

    public static void getTasksByDay(Scanner scanner) {
        System.out.println("Введите дату как dd.MM.yyyy:");
        try{
            String date = scanner.next();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate requestedDate = LocalDate.parse(date, dateFormatter);
            List<Repeatable> foundEvents = findTasksByDate(requestedDate);
            System.out.println("События на " + requestedDate + ":");
            for (Repeatable task : foundEvents){
                System.out.println(task);
            }
        } catch (DateTimeParseException e) {
            System.out.println("Проверьте формат даты dd.MM.yyyy и попробуйте ещё раз. ");
        }
        scanner.nextLine();
        System.out.println("Для выхода нажмите Enter\n");
    }

    public static void printArchivedTasks() {
        for (Repeatable task : archivedTasks.values()){
            System.out.println(task);
        }
    }

    public static void getGroupedByDate(){
        Map<LocalDate, ArrayList<Repeatable>> taskMap = new HashMap<>();

        for (Map.Entry<Integer,Repeatable> entry : actualTasks.entrySet()){
            Repeatable task = entry.getValue();
            LocalDate localDate = task.getFirstDate().toLocalDate();
            if (taskMap.containsKey(localDate)) {
                ArrayList<Repeatable> tasks = taskMap.get(localDate);
                tasks.add(task);
            } else {
                taskMap.put(localDate, new ArrayList<>(Collections.singletonList(task)));
            }
        }
        for (Map.Entry<LocalDate, ArrayList<Repeatable>> taskEntry : taskMap.entrySet()) {
            System.out.println(taskEntry.getKey() + " : " + taskEntry.getValue());
        }
    }

    private static  List<Repeatable> findTasksByDate (LocalDate date) {
        List<Repeatable> tasks = new ArrayList<>();
        for (Repeatable task : actualTasks.values()) {
            if (task.checkDates(date.atStartOfDay())){
                tasks.add(task);
            }
        }
        return tasks;
    }

    private static Repeatable createTask (int period, String title, String description, TaskType taskType, LocalDateTime localDateTime) throws TaskNotFoundException  {
        return switch (period) {
            case 0 ->{
                OneTimeTask oneTimeTask = new OneTimeTask(title, description, taskType, localDateTime);
                actualTasks.put(oneTimeTask.getId(), oneTimeTask);
                yield oneTimeTask;
            }
            case 1 -> {
                DailyTask dailyTask = new DailyTask(title, description, taskType, localDateTime);
                actualTasks.put(dailyTask.getId(), dailyTask);
                yield dailyTask;
            }
            case 2 -> {
                WeeklyTask weeklyTask = new WeeklyTask(title, description, taskType, localDateTime);
                actualTasks.put(weeklyTask.getId(), weeklyTask);
                yield weeklyTask;
            }
            case 3 -> {
                MonthlyTask monthlyTask = new MonthlyTask(title, description, taskType, localDateTime);
                actualTasks.put(monthlyTask.getId(), monthlyTask);
                yield monthlyTask;
            }
            case 4 -> {
                AnnualTask annualTask = new AnnualTask(title, description, taskType, localDateTime);
                actualTasks.put(annualTask.getId(), annualTask);
                yield annualTask;
            }
            default -> null;
        };
    }

    private static void printActualTasks() {
        for (Repeatable task : actualTasks.values()) {
            System.out.println(task);
        }
    }
}
