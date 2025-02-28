package bluebird;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import bluebird.exceptions.IllegalTaskParameterException;
import bluebird.tasks.*;

/**
 * Handles the creation of Tasks from user input. Creates the appropriate Task type 
 * and prompts the user for more details if insufficient was provided.
 */
public class TaskFactory {
    private static UIHandler ui;
    private static final String REGEX_DEADLINE_STRING = "/by|/b";
    private static final String REGEX_EVENT_STRING = "((?=/from)|(?=/f)|(?=/to)|(?=/t))";
    private static final String REGEX_EVENT_NODELIM_STRING = "/from|/f|/to|/t";

    /**
     * A map that associates each task type with its corresponding create function.
     * <p>
     * The create function takes a string of arguments and returns a {@link Task} object.
     * </p>
     */
    private static final Map<TaskType, Function<String, Task>> createTaskMap = new HashMap<>();

    static {
        createTaskMap.put(TaskType.TODO, TaskFactory::createToDo);
        createTaskMap.put(TaskType.DEADLINE, TaskFactory::createDeadline);
        createTaskMap.put(TaskType.EVENT, TaskFactory::createEvent);
    }

    public TaskFactory(UIHandler ui) {
        TaskFactory.ui = ui;
    }

    /**
     * Takes the user-specified task type and calls its appropriate create function with 
     * the necessary arguments.
     * 
     * @param taskType user-specified task type
     * @param details task details such as description or due date
     * @return a Task object
     */
    public static Task createTask (String taskType, String details) {
        Function<String, Task> creator = createTaskMap.get(TaskType.fromString(taskType.toLowerCase()));
        if (creator == null) {
            return null;
        }
        return creator.apply(details);
    }
            
    private static ToDo createToDo(String description) {
        if (description.trim().isEmpty()) {
            return null;
        }
        return new ToDo(description);
    }

    /**
     * Creates a deadline Task. Returns a null object if the end date is not specified even 
     * after user prompting.
     * 
     * @param details String containing the description and end date for the event. Must not 
     * be empty
     * @return a Deadline object
     */
    private static Deadline createDeadline(String details) {
        String[] parts = details.split(REGEX_DEADLINE_STRING, 2);
        String by, description = parts[0].trim();

        try {
            if (parts.length < 2) {
                by = ui.promptUser(description + "\nWhen is this due? Don't include /by here");
            } else {
                by = parts[1].trim();
            }
        } catch (IllegalTaskParameterException e) {
            return null;
        }
        return new Deadline(description, by);
    }

    /**
     * Creates an event Task. Returns a null object if either the start or end date is not 
     * specified even after user prompting.
     * 
     * @param details description, start and end date for the event. Must not be empty
     * @return an Event object
     */
    private static Event createEvent(String details) {
        String[] parts = details.split(REGEX_EVENT_STRING, 3);
        String from = "", to = "";
        String description = parts[0].trim();
        
        try {
            if (parts.length == 1) {
                from = ui.promptUser(description + "\nWhen does this begin? Don't include /from here");
                to = ui.promptUser(description + " (from: " + from + ")\nWhen will this end? Don't include /to here");
            } else if (parts.length == 2) {
                String[] partsNoDelim = details.split(REGEX_EVENT_NODELIM_STRING, 2);
                if (parts[1].contains("/f")) {
                    from = partsNoDelim[1].trim();
                    to = ui.promptUser(description + " (from: " + from + ")\nWhen will this end? Don't include /to here");
                } else {
                    to = partsNoDelim[1].trim();
                    from = ui.promptUser(description + " (to: " + to + ")\nWhen does this begin? Don't include /from here");
                }
            } else {
                String[] partsNoDelim = details.split(REGEX_EVENT_NODELIM_STRING, 3);

                if (parts[1].contains("/f")) {
                    from = partsNoDelim[1].trim();
                    to = partsNoDelim[2].trim();
                } else {
                    from = partsNoDelim[2].trim();
                    to = partsNoDelim[1].trim();
                }
            }
        } catch (IllegalTaskParameterException e) {
            return null;
        }

        return new Event(description, from, to);
    }

    /**
     * Parses a singular line from a save file and converts the stored details to be 
     * back into Task objects.
     * 
     * @param fileString String stream from the save file
     * @return a Task object
     */
    public static Task createTaskFromFileString(String fileString) {
        String[] parts = fileString.split(" \\| ");
        if (parts.length < 3) {
            return null;
        }

        String taskType = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        switch (taskType) {
        case "T":
            ToDo todo = new ToDo(description);
            todo.markDone(isDone);
            return todo;
        case "E":
            if (parts.length < 5) {
                return null;
            }
            Event event = new Event(description, parts[3], parts[4]);
            event.markDone(isDone);
            return event;
        case "D":
            if (parts.length < 4) {
                return null;
            }
            Deadline deadline = new Deadline(description, parts[3]);
            deadline.markDone(isDone);
            return deadline;
        default: return null;
        }
    }
}
