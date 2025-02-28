package bluebird.commands.taskcommands;

import bluebird.TaskManager;
import bluebird.commands.Command;

/**
 * Represents a command to remove an existing task from the task manager,
 * based on its index number.
 * <p>
 * The task indices must be valid and non-repeating.
 * The actual task deletion is delegated to the {@link TaskManager}.
 * </p>
 * <p>
 * If the task deletion is successful, a feedback message is stored in the
 * {@code commandFeedback} attribute. If the task creation fails, an error message
 * is stored instead.
 * </p>
 *
 * @see Command
 * @see TaskCommand
 * @see TaskManager
 */
public class DeleteCommand extends Command implements TaskCommand {
    private final TaskManager taskManager;
    private final int[] taskIndices;
    
    /**
     * Constructs a {@code DeleteCommand} with the specified task manager, and task indices array.
     *
     * @param taskManager the task manager to which the task will be deleted from
     * @param taskIndices the array storing task indices for deletion
     */
    public DeleteCommand(TaskManager taskManager, int[] taskIndices) {
        this.taskManager = taskManager;
        this.taskIndices = taskIndices;
    }

    /**
     * Executes the Delete command by removing the selected task(s) from the task manager.
     * <p>
     * The task(s) is removed using the {@link TaskManager#deleteTask(int[])} method.
     * If the task deletion is successful, the tasks are added to a temporary array, and a
     * success message is stored in the {@code commandFeedback} attribute. 
     * Otherwise, an error message is stored instead.
     * </p>
     * <p>
     * This method always returns {@code false} to indicate that the program should
     * continue running after executing this command.
     * </p>
     *
     * @return {@code false} to signal that the program should not end
     * @see TaskManager#deleteTask(int[])
     */
    @Override
    public boolean execute() {
        commandFeedback = taskManager.deleteTask(taskIndices);
        return false;
    }

    /**
     * Returns a help message that includes a hint on the expected format for deleting tasks.
     *
     * @return a string containing the help message
     */
    public String showHelpString() {
        return "\tHint: delete task_number [task_number ...]"; // implement THIS
    }
}
