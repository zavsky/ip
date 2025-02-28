package bluebird.commands;

import java.util.HashMap;
import java.util.Map;

import bluebird.CommandType;

/**
 * Represents a command to display help information to the user.
 * <p>
 * The general help message includes all available commands and their syntax.
 * The specific hint provides a concise example for a single command type.
 * </p>
 *
 * @see Command
 */
public class HelpCommand extends Command {
    /**
     * A map that associates each command type with its usage example.
     */
    private static final Map<CommandType, String> commandExample = new HashMap<>();

    static {
        commandExample.put(CommandType.ADD, "add d/e/t task_description");
        commandExample.put(CommandType.MARK, "mark task_index [task_indices ...]");
        commandExample.put(CommandType.UNMARK, "unmark task_index [task_indices ...]");
        commandExample.put(CommandType.DELETE, "delete task_index [task_indices ...]");
    }

    /**
     * Constructs a {@code HelpCommand} that displays a general help message.
     * <p>
     * The general help message includes all available commands and their syntax.
     * </p>
     */
    public HelpCommand() {
        commandFeedback = "Available commands:\n" +
            "\tlist, l      - List all tasks\n" +
            "\tadd, a       - Add a new task                \"a d/e/t description\"\n" +
            "\tmark, m      - Mark a task as done           \"m task_index\"\n" +
            "\tunmark, u    - Mark a task as not done       \"u task_index\"\n" +
            "\tdelete, d    - Delete a task                 \"d task_index\"\n" +
            "\tundo, z      - Undo the last command\n" +
            "\texit, e      - Exit the program\n" +
            "\thelp, h      - Show this help message\n\n" +
            "\tadd options\n" +
            "\t    deadline   task_description [/by deadline_string]\n" +
            "\t    event      task_description [/from start_string] [/to end_string]\n" +
            "\t    todo       task_description";
    }

    /**
     * Constructs a {@code HelpCommand} that displays a specific hint for a given command type.
     *
     * @param commandList the command type for which to display the hint
     * @see CommandType
     */
    public HelpCommand(CommandType commandList) {
        commandFeedback = "Hint: " + commandExample.get(commandList);
    }

    /**
     * Executes the help command by displaying the help message stored in {@code commandFeedback}.
     * <p>
     * This method does not perform any additional actions and always returns {@code false}
     * to indicate that the program should continue running.
     * </p>
     *
     * @return {@code false} to signal that the program should not end
     */
    @Override
    public boolean execute() {
        return false;
    }
}
