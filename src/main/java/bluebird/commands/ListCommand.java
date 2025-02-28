package bluebird.commands;

import bluebird.TaskManager;

/**
 * Represents a command to display the saved tasks to the user.
 *
 * @see Command
 */
public class ListCommand extends Command {
    private final TaskManager taskManager;

    public ListCommand(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    /**
     * Executes the List command by displaying the tasks string stored in {@code commandFeedback}.
     * <p>
     * This method adds a string-formatted task list to {@code commandFeedback} and always returns {@code false}
     * to indicate that the program should continue running.
     * </p>
     *
     * @return {@code false} to signal that the program should not end
     */
    @Override
    public boolean execute() {
        commandFeedback = taskManager.getPrintableTasks();
        return false;
    }
}
