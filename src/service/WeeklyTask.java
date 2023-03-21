package service;

import exceptions.TaskNotFoundException;

import java.time.LocalDateTime;

public class WeeklyTask extends Task implements Repeatable{
    public WeeklyTask(String title, String description, TaskType taskType, LocalDateTime date) throws TaskNotFoundException {
        super(title, description, taskType, date);
    }

    @Override
    public boolean checkDates(LocalDateTime requestedDate){
        return getFirstDate().getDayOfWeek().equals(requestedDate.getDayOfWeek());
    }
}
