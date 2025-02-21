package bluebird.commands.taskcommands;

import bluebird.TaskManager;
import bluebird.commands.Command;

public class MarkCommand extends Command implements TaskCommand {
    private final TaskManager taskManager;
    private final int taskIndex;
    private final boolean markAsDone;

    public MarkCommand(TaskManager taskManager, int taskIndex, boolean markAsDone) {
        this.taskManager = taskManager;
        this.taskIndex = taskIndex;
        this.markAsDone = markAsDone;
    }

    /**
     * Toggles the Task completion (markAsDone) flag according to user input.
     * Prints a success message.
     * @return false to signal that the program should not end.
     */
    @Override
    public boolean execute() {
        commandFeedback = taskManager.markTask(taskIndex, markAsDone);
        return false;
    }

    @Override
    public String showHelpString() {
        return "Syntax:\nmark task_number\nunmark task_number"; // implement THIS
    }
}
