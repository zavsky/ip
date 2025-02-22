package bluebird;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import bluebird.commands.*;
import bluebird.commands.taskcommands.*;
import bluebird.tasks.TaskType;

public class CommandParser {
    private final TaskManager taskManager;
    private final TaskFactory taskFactory;
    private final UIHandler ui;

    private static final Map<String, CommandType> COMMAND_MAP = new HashMap<>();
    private static final Map<String, TaskType> TASK_TYPES = new HashMap<>();

    static {
        COMMAND_MAP.put("list", CommandType.LIST);
        COMMAND_MAP.put("l", CommandType.LIST);
        COMMAND_MAP.put("add", CommandType.ADD);
        COMMAND_MAP.put("a", CommandType.ADD);
        COMMAND_MAP.put("mark", CommandType.MARK);
        COMMAND_MAP.put("m", CommandType.MARK);
        COMMAND_MAP.put("unmark", CommandType.UNMARK);
        COMMAND_MAP.put("u", CommandType.UNMARK);
        COMMAND_MAP.put("delete", CommandType.DELETE);
        COMMAND_MAP.put("d", CommandType.DELETE);
        COMMAND_MAP.put("undo", CommandType.UNDO);
        COMMAND_MAP.put("z", CommandType.UNDO);
        COMMAND_MAP.put("help", CommandType.HELP);
        COMMAND_MAP.put("h", CommandType.HELP);
        COMMAND_MAP.put("exit", CommandType.EXIT);
        COMMAND_MAP.put("e", CommandType.EXIT);

        TASK_TYPES.put("deadline", TaskType.DEADLINE);
        TASK_TYPES.put("d", TaskType.DEADLINE);
        TASK_TYPES.put("event", TaskType.EVENT);
        TASK_TYPES.put("e", TaskType.EVENT);
        TASK_TYPES.put("todo", TaskType.TODO);
        TASK_TYPES.put("t", TaskType.TODO);
    }
    
    public CommandParser(TaskManager taskManager, TaskFactory taskFactory, UIHandler ui) {
        this.taskManager = taskManager;
        this.taskFactory = taskFactory;
        this.ui = ui;
    }
    
    public Command parseInput(String input) {
        String[] parts = input.split(" ", 2);
        String commandString = parts[0].toLowerCase();
        Optional<String> arguments = parts.length > 1 ? Optional.of(parts[1].trim()) : Optional.empty();

        CommandType commandType = COMMAND_MAP.getOrDefault(commandString, CommandType.UNKNOWN);

        switch (commandType) {
        case LIST:
            return new ListCommand(taskManager);
        case ADD:
            return parseAddCommand(arguments.orElse(""));
        case MARK:
            return parseMarkCommand(arguments.orElse(""), true);
        case UNMARK:
            return parseMarkCommand(arguments.orElse(""), false);
        case DELETE:
            return parseDeleteCommand(arguments.orElse(""));
        case UNDO:
            return new UndoCommand(taskManager);
        case HELP:
            return new HelpCommand();
        case EXIT:
            return new ExitCommand();
        default:
            ui.showConfused();
            return new HelpCommand();
        }
    }
    
    private Command parseAddCommand(String arguments) {
        String[] parts = getValidTaskTypeAndDetails(arguments, "Add event, deadline or todo? ");
        if (parts == null) {
            return null;
        }

        String taskType = parts[0].toLowerCase();
        String details = (parts.length > 1) ? parts[1] : ui.getUserInput("Enter details: ");

        if (details.trim().isEmpty()) {
            return null;
        }

        return new AddCommand(taskManager, taskFactory, taskType, details);
    }

    private String[] getValidTaskTypeAndDetails(String input, String prompt) {
        String[] parts = input.split(" ", 2);
        String taskType = parts[0].toLowerCase();

        if (!TASK_TYPES.containsKey(taskType)) {
            input = ui.getUserInput(prompt).trim();
            parts = input.split(" ", 2);
            taskType = parts[0].toLowerCase();

            if (!TASK_TYPES.containsKey(taskType)) {
                return null;
            }
        }
        return parts;
    }

    private Command parseMarkCommand(String arguments, boolean markAsDone) {
        if (taskManager.isEmpty()) {
            return null;
        }

        String args = arguments;
        if (args.isEmpty()) {
            ui.showTasks(taskManager.getPrintableTasks(), (markAsDone ? MessageType.MARK : MessageType.UNMARK));

            args = ui.getUserInput("Enter task numbers to delete (separated by spaces): ").trim();
        }
        if (args.isEmpty()) {
            return null;
        }

        int[] taskIndices = parseTaskIndices(args);
        if (taskIndices == null || taskIndices.length == 0) {
            ui.showMessage(MessageType.ERROR, "Invalid indices for deletion");
            return new EmptyCommand();
        }

        return new MarkCommand(taskManager, taskIndices, markAsDone);
    }

    private Command parseDeleteCommand(String arguments) {
        if (taskManager.isEmpty()) {
            return null;
        }
        
        String args = arguments;
        if (args.isEmpty()) {
            ui.showTasks(taskManager.getPrintableTasks(), MessageType.DELETE);
            
            args = ui.getUserInput("Enter task numbers to delete (separated by spaces): ").trim();
        }
        if (args.isEmpty()) {
            return null;
        }

        // Parse the input into an array of integers
        int[] taskIndices = parseTaskIndices(args);
        if (taskIndices == null || taskIndices.length == 0) {
            ui.showMessage(MessageType.ERROR, "Invalid indices for deletion");
            return new EmptyCommand();
        }

        return new DeleteCommand(taskManager, taskIndices);
    }

    // private Command parseDeleteCommand(String arguments) {
    //     if (taskManager.isEmpty()) {
    //         return null;
    //     }
    //     String args = arguments;
    //     if (args.isEmpty()) {
    //         ui.showTasks(taskManager.getPrintableTasks(), MessageType.DELETE);
    //         args = ui.getUserInput("Enter task number to delete: ").trim();
    //     }
    //     if (args.isEmpty()) {
    //         return null;
    //     }
    //     int taskIndex = parseTaskIndex(args);
    //     if (taskIndex == -1) {
    //         ui.showMessage(MessageType.ERROR, "Index for deletion does not make sense");
    //         return new EmptyCommand();
    //     }
    //     return new DeleteCommand(taskManager, taskIndex);
    // }

    // private int parseTaskIndex(String input) {
    //     try {
    //         int index = Integer.parseInt(input.trim()) - 1;
    //         // check index within bounds
    //         if (index >= 0 && index < taskManager.getTaskCount()) {
    //             return index;
    //         }
    //     } catch (NumberFormatException e) {
    //         // invalid number format
    //     }
    //     return -1;
    // }

    /**
     * Parses a string of space-separated integers into an array of integers.
     * Returns null if any part of the input is invalid.
     */
    private int[] parseTaskIndices(String input) {
        String[] parts = input.split("\\s+"); // Split by spaces
        Integer[] indices = new Integer[parts.length];
        try {
            for (int i = 0; i < parts.length; i++) {
                int index = Integer.parseInt(parts[i].trim()) - 1;
                // Validate that the index is non-negative
                if (index < 0) {
                    return null;
                }
                // Check index is within range
                if (index < 0 || index >= taskManager.getTaskCount()) {
                    return null;
                }
                indices[i] = index;
            }
            Arrays.sort(indices, Collections.reverseOrder());
            return Arrays.stream(indices).mapToInt(Integer::intValue).toArray();
        } catch (NumberFormatException e) {
            // If any part of the input is not a valid integer, return null
            return null;
        }
    }
}
