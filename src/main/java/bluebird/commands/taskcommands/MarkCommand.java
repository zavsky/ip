package bluebird.commands.taskcommands;

import bluebird.TaskManager;
import bluebird.commands.Command;

public class MarkCommand extends Command implements TaskCommand {
    private final TaskManager taskManager;
    private final int[] taskIndices;
    private final boolean markAsDone;

    public MarkCommand(TaskManager taskManager, int[] taskIndices, boolean markAsDone) {
        this.taskManager = taskManager;
        this.taskIndices = taskIndices;
        this.markAsDone = markAsDone;
    }

    /**
     * Toggles Task completion via TaskManager using user-specified index.
     * Adds a message to the @attribute commandFeedback if successful.
     * @return false to signal that the program should not end.
     */
    @Override
    public boolean execute() {
        commandFeedback = taskManager.markTask(taskIndices, markAsDone);
        return false;
    }

    public String showHelpString() {
        return "Syntax:\nmark task_number [task_number ...]\nunmark task_number [task_number ...]"; // implement THIS
    }
}
