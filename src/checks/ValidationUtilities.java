package checks;

import exceptions.TaskNotFoundException;

public class ValidationUtilities {
    public  static String validateString (String value) throws TaskNotFoundException{
        if (value == null || value.isEmpty() || value.isBlank()) {
            throw new TaskNotFoundException ("Некорректный ввод");
        } else {
            return value;
        }
    }
}