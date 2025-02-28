package bluebird.commands;

import bluebird.TaskManager;

/**
 * Represents a command to undo the last user action.
 *
 * @see Command
 */
public class UndoCommand extends Command {
    private final TaskManager taskManager;

    public UndoCommand(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    /**
     * Undoes the last user-specified command via the task manager. Does nothing 
     * if there is no previous command.
     * <p>
     * This method stores the log of the undone action in {@code commandFeedback} 
     * and always returns {@code false}
     * to indicate that the program should continue running.
     * </p>
     *
     * @return {@code false} to signal that the program should not end
     */
    @Override
    public boolean execute() {
        commandFeedback = taskManager.undoCommand();
        return false;
    }
}
