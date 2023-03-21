package service;

import exceptions.TaskNotFoundException;

import java.time.LocalDateTime;

public class AnnualTask extends Task implements Repeatable{
    public AnnualTask(String title, String description, TaskType taskType, LocalDateTime date) throws TaskNotFoundException {
        super(title, description, taskType, date);
    }

    @Override
    public boolean checkDates(LocalDateTime requestedDate){
        return getFirstDate().getYear() == (requestedDate.getYear());
    }
}
