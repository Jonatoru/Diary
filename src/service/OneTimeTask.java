package service;

import exceptions.TaskNotFoundException;

import java.time.LocalDateTime;

public class OneTimeTask extends Task implements Repeatable {
    public OneTimeTask(String title, String description, TaskType taskType, LocalDateTime date) throws TaskNotFoundException {
        super(title,description,taskType,date);
    }

    @Override
    public boolean checkDates(LocalDateTime requestedDate){
        return getFirstDate().toLocalDate().equals(requestedDate.toLocalDate());
    }
}
