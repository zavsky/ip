package bluebird;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import bluebird.commands.*;
import bluebird.commands.taskcommands.*;
import bluebird.tasks.TaskType;

public class CommandParser {
    private final TaskManager taskManager;
    private final TaskFactory taskFactory;
    private final UIHandler ui;

    private final Map<CommandType, Function<String, Command>> commandParsers = new HashMap<>();
    
    public CommandParser(TaskManager taskManager, TaskFactory taskFactory, UIHandler ui) {
        this.taskManager = taskManager;
        this.taskFactory = taskFactory;
        this.ui = ui;

        commandParsers.put(CommandType.LIST, args -> new ListCommand(taskManager));
        commandParsers.put(CommandType.ADD, this::parseAddCommand);
        commandParsers.put(CommandType.MARK, args -> parseMarkCommand(args, true));
        commandParsers.put(CommandType.UNMARK, args -> parseMarkCommand(args, false));
        commandParsers.put(CommandType.DELETE, this::parseDeleteCommand);
        commandParsers.put(CommandType.UNDO, args -> new UndoCommand(taskManager));
        commandParsers.put(CommandType.HELP, args -> new HelpCommand());
        commandParsers.put(CommandType.EXIT, args -> new ExitCommand());
    }
    
    public Command parseInput(String input) {
        String[] parts = input.split(" ", 2);
        String commandString = parts[0].toLowerCase();
        Optional<String> arguments = parts.length > 1 ? Optional.of(parts[1].trim()) : Optional.empty();

        CommandType commandType = CommandType.fromString(commandString);

        Function<String, Command> parser = commandParsers.getOrDefault(commandType, args -> {
            ui.showConfused();
            return new HelpCommand();
        });

        return parser.apply(arguments.orElse(""));
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

        if (TaskType.fromString(taskType) == null) {
            input = ui.getUserInput(prompt).trim();
            parts = input.split(" ", 2);
            taskType = parts[0].toLowerCase();

            if (TaskType.fromString(taskType) == null) {
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

    /**
     * Parses a string of space-separated integers into an array of integers.
     * Returns null if any part of the input is invalid.
     */
    private int[] parseTaskIndices(String input) {
        String[] parts = input.split("\\s+"); // Split by spaces
        int[] indices = new int[parts.length];
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
            indices = removeDuplicateFromArray(indices);
            return indices;
        } catch (NumberFormatException e) {
            // If any part of the input is not a valid integer, return null
            return null;
        }
    }

    private static int[] removeDuplicateFromArray(int[] a) {
        Arrays.sort(a);
        int aLen = a.length;
        int[] temp = new int[aLen];
        int j = 0;
        for (int i = 0; i < aLen-1; i++) {
            if (a[i] != a[i+1]) {
                temp[j++] = a[i];
            }
        }
        temp[j++] = a[aLen-1];

        return Arrays.copyOf(temp, j);
    }
}
