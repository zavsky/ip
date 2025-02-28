package bluebird.commands.taskcommands;

import bluebird.TaskManager;
import bluebird.commands.Command;

/**
 * Represents a command to toggle the completion of a task in the task manager,
 * based on its index number.
 * <p>
 * The task indices must be valid and non-repeating.
 * The actual task (un)marking is delegated to the {@link TaskManager}.
 * </p>
 * <p>
 * If the marking of the task(s) is successful, a feedback message is stored in the
 * {@code commandFeedback} attribute. If the task creation fails, an error message
 * is stored instead.
 * </p>
 *
 * @see Command
 * @see TaskCommand
 * @see TaskManager
 */
public class MarkCommand extends Command implements TaskCommand {
    private final TaskManager taskManager;
    private final int[] taskIndices;
    private final boolean markAsDone;

    /**
     * Constructs a {@code MarkCommand} with the specified task manager, task indices array, 
     * and a completion indicator.
     *
     * @param taskManager the task manager to which the task will be marked from
     * @param taskIndices the array storing task indices for marking
     * @param markAsDone  the flag indicating task completion
     */
    public MarkCommand(TaskManager taskManager, int[] taskIndices, boolean markAsDone) {
        this.taskManager = taskManager;
        this.taskIndices = taskIndices;
        this.markAsDone = markAsDone;
    }

    /**
     * Executes the Mark command by marking the completion of the selected tasks via the task manager.
     * <p>
     * The task is marked using the {@link TaskManager#markTask(int[])} method.
     * If the task marking is successful, the tasks are added to a temporary array, and a
     * success message is stored in the {@code commandFeedback} attribute. 
     * Otherwise, an error message is stored instead.
     * </p>
     * <p>
     * This method always returns {@code false} to indicate that the program should
     * continue running after executing this command.
     * </p>
     *
     * @return {@code false} to signal that the program should not end
     * @see TaskManager#markTask(int[])
     */
    @Override
    public boolean execute() {
        commandFeedback = taskManager.markTask(taskIndices, markAsDone);
        return false;
    }

    /**
     * Returns a help message that includes a hint on the expected format for marking tasks.
     *
     * @return a string containing the help message
     */
    public String showHelpString() {
        return "\tHint: mark task_number [task_number ...]\n\t      unmark task_number [task_number ...]"; // implement THIS
    }
}
