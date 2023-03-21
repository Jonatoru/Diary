package service;

import exceptions.TaskNotFoundException;

import java.time.LocalDateTime;

public class MonthlyTask extends Task implements Repeatable{
    public MonthlyTask(String title, String description, TaskType taskType, LocalDateTime date) throws TaskNotFoundException {
        super(title,description,taskType,date);
    }
    @Override
    public boolean checkDates(LocalDateTime requestedDate){
        return getFirstDate().getDayOfMonth() == (requestedDate.getDayOfMonth());
    }
}
