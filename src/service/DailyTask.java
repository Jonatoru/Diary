package service;

import exceptions.TaskNotFoundException;

import java.time.LocalDateTime;

public class DailyTask extends Task implements Repeatable{
    public DailyTask(String title, String description, TaskType taskType, LocalDateTime date) throws TaskNotFoundException {
        super(title,description,taskType,date);
    }

    @Override
    public boolean checkDates(LocalDateTime requestedDate){
        return getFirstDate().toLocalDate().equals(requestedDate.toLocalDate());
    }
}
