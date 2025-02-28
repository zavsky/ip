package bluebird.commands;

import java.util.HashMap;
import java.util.Map;

import bluebird.CommandType;

public class HelpCommand extends Command {
    private static final Map<CommandType, String> commandExample = new HashMap<>();

    static {
        commandExample.put(CommandType.ADD, "add d/e/t task_description");
        commandExample.put(CommandType.MARK, "mark task_index [task_indices ...]");
        commandExample.put(CommandType.UNMARK, "unmark task_index [task_indices ...]");
        commandExample.put(CommandType.DELETE, "delete task_index [task_indices ...]");
    }

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

    public HelpCommand(CommandType commandList) {
        commandFeedback = "Hint: " + commandExample.get(commandList);
    }

    /**
     * Writes a help string to @attribute commandFeedback.
     * @return false to signal that the program should not end.
     */
    @Override
    public boolean execute() {
        return false;
    }
}
