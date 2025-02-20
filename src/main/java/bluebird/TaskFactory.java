package bluebird;

import bluebird.exceptions.IllegalTaskParameterException;
import bluebird.tasks.*;

public class TaskFactory {
    /**
     * @throws IllegalTaskParameterException 
     * 
     */
    public static Task createTask (String taskType, String details) throws IllegalTaskParameterException {
        switch (taskType.toLowerCase()) {
        case "todo":
        case "t":
            return createToDo(details);
        case "deadline":
        case "d":
            return createDeadline(details);
        case "event":
        case "e":
            return createEvent(details);
        default:
            throw new IllegalTaskParameterException();
        }
    }

    private static ToDo createToDo(String description) throws IllegalTaskParameterException {
        if (description.trim().isEmpty()) {
            throw new IllegalTaskParameterException();
        }
        return new ToDo(description);
    }

    private static Deadline createDeadline(String details) throws IllegalTaskParameterException {
        String[] parts = details.split("/by|/b", 2);

        if (parts.length < 2) {
            // throw exception
            throw new IllegalTaskParameterException();
        }

        String description = parts[0].trim();

        if (description.trim().isEmpty()) {
            throw new IllegalTaskParameterException();
        }

        String by = parts[1].trim();
        return new Deadline(description, by);
    }

    private static Event createEvent(String details) throws IllegalTaskParameterException {
        String[] parts = details.split("/from|/to|/f|/t", 3);
        
        if (parts.length < 3) {
            // throw exception
            throw new IllegalTaskParameterException();
        }

        String description = parts[0].trim();

        if (description.trim().isEmpty()) {
            throw new IllegalTaskParameterException();
        }

        String from = parts[1].trim();
        String to = parts[2].trim();
        return new Event(description, from, to);
    }
}
