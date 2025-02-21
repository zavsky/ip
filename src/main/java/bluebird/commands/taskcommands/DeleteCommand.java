package bluebird.commands.taskcommands;

import java.util.ArrayList;

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
     * Delete a task via the index specified by the user.
     * Prints a success message.
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
