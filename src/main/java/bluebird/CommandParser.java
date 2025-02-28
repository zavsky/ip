package bluebird;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import bluebird.commands.*;
import bluebird.commands.taskcommands.*;
import bluebird.tasks.TaskType;

/**
 * Parses user input into executable commands.
 * <p>
 * This class is responsible for interpreting user input and converting it into
 * appropriate command objects. It uses a map of command parsers to handle
 * different types of commands (e.g., add, mark, delete).
 * </p>
 * <p>
 * The parser supports commands such as adding tasks, marking tasks as done or not done,
 * deleting tasks, and displaying help or exit messages.
 * </p>
 *
 * @see Command
 * @see TaskManager
 * @see UIHandler
 */
public class CommandParser {
    private final TaskManager taskManager;
    private final UIHandler ui;

    /**
     * A map that associates each command type with its corresponding parser function.
     * <p>
     * The parser function takes a string of arguments and returns a {@link Command} object.
     * </p>
     */
    private final Map<CommandType, Function<String, Command>> commandParsers = new HashMap<>();

    public CommandParser(TaskManager taskManager, UIHandler ui) {
        this.taskManager = taskManager;
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
    
    /**
     * Parses the user input and returns the corresponding command.
     * <p>
     * The input string is split into a command and its arguments. The command is then
     * mapped to a parser function, which is used to create the appropriate command object.
     * </p>
     * <p>
     * If the command is not recognized, a help message is displayed, and a {@link HelpCommand}
     * is returned.
     * </p>
     *
     * @param input the user input to parse
     * @return the parsed command, or a {@link HelpCommand} if the input is invalid
     */
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
    
    /**
     * Parses the arguments for an "add" command and returns the corresponding {@link AddCommand}.
     * <p>
     * The arguments are validated to ensure they contain a valid task type and description.
     * If the arguments are incomplete, the user is prompted to provide the missing details.
     * </p>
     *
     * @param arguments the arguments for the "add" command
     * @return the parsed {@link AddCommand}, or {@code null} if the arguments are invalid
     */
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

        return new AddCommand(taskManager, taskType, details);
    }

    /**
     * Validates the task type and details provided in the input.
     * <p>
     * If the task type is invalid, the user is prompted to provide a valid task type.
     * </p>
     *
     * @param input the input string containing the task type and details
     * @param prompt the prompt to display if the task type is invalid
     * @return an array containing the task type and details, or {@code null} if the input is invalid
     */
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

    /**
     * Parses the arguments for a "mark" or "unmark" command and returns the corresponding command.
     * <p>
     * The arguments are validated to ensure they contain valid task indices. If the arguments
     * are incomplete, the user is prompted to provide the missing indices.
     * </p>
     *
     * @param arguments the arguments for the "mark" or "unmark" command
     * @param markAsDone {@code true} to mark tasks as done, {@code false} to mark them as not done
     * @return the parsed {@link MarkCommand} or {@link UnmarkCommand}, or {@code null} if the arguments are invalid
     */
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

    /**
     * Parses the arguments for a "delete" command and returns the corresponding {@link DeleteCommand}.
     * <p>
     * The arguments are validated to ensure they contain valid task indices. If the arguments
     * are incomplete, the user is prompted to provide the missing indices.
     * </p>
     *
     * @param arguments the arguments for the "delete" command
     * @return the parsed {@link DeleteCommand}, or {@code null} if the arguments are invalid
     */
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
     * <p>
     * The input string is split into individual parts, and each part is converted to an integer.
     * The indices are validated to ensure they are non-negative and within the range of the task list.
     * Duplicate indices are removed.
     * </p>
     *
     * @param input the input string containing space-separated integers
     * @return an array of valid task indices, or {@code null} if the input is invalid
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

    /**
     * Removes duplicate values from an array of integers.
     * <p>
     * The array is sorted, and duplicate values are removed. The resulting array
     * contains only unique values in ascending order.
     * </p>
     *
     * @param a the array of integers
     * @return a new array containing only unique values
     */
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
