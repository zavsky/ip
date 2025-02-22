package bluebird.commands.taskcommands;

import bluebird.TaskManager;
import bluebird.commands.Command;

public class DeleteCommand extends Command implements TaskCommand {
    private final TaskManager taskManager;
    private final int taskIndex;
    
    public DeleteCommand(TaskManager taskManager, int taskIndex) {
        this.taskManager = taskManager;
        this.taskIndex = taskIndex;
    }

    /**
     * Deletes a task via TaskManager using user-specified index.
     * Adds a message to the @attribute commandFeedback if successful.
     * @return false to signal that the program should not end.
     */
    @Override
    public boolean execute() {
        commandFeedback = taskManager.deleteTask(taskIndex);
        return false;
    }

    @Override
    public String showHelpString() {
        return "Syntax:\ndelete task_number"; // implement THIS
    }
}
