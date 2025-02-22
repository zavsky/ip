package bluebird.commands.taskcommands;

import bluebird.TaskManager;
import bluebird.commands.Command;

public class DeleteCommand extends Command implements TaskCommand {
    private final TaskManager taskManager;
    private final int[] taskIndices;
    
    public DeleteCommand(TaskManager taskManager, int[] taskIndices) {
        this.taskManager = taskManager;
        this.taskIndices = taskIndices;
    }

    /**
     * Deletes multiple tasks via TaskManager using user-specified indices.
     * Adds a message to the @attribute commandFeedback if successful.
     * @return false to signal that the program should not end.
     */
    @Override
    public boolean execute() {
        commandFeedback = taskManager.deleteTask(taskIndices);
        return false;
    }

    public String showHelpString() {
        return "Syntax:\ndelete task_number [task_number ...]"; // implement THIS
    }
}
