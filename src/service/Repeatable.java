package service;

import java.time.LocalDateTime;

public interface Repeatable {

    boolean checkDates(LocalDateTime localDateTime);

    void setTitle(String title);

    void setDescription(String description);

    void setTaskType(TaskType taskType);

    void setFirstDate(LocalDateTime firstDate);

    LocalDateTime getFirstDate();

    void  setArchived(boolean archived);
}
