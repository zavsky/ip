package bluebird.commands.taskcommands;

import bluebird.TaskFactory;
import bluebird.TaskManager;
import bluebird.commands.Command;
import bluebird.tasks.Task;

/**
 * Represents a command to add a new task to the task manager.
 * This command creates a task based on the provided task type and details,
 * and adds it to the task manager.
 * <p>
 * The task type and details must be non-empty, but their validity is not checked
 * by this class. The actual task creation is delegated to the {@link TaskFactory}.
 * </p>
 * <p>
 * If the task creation is successful, a feedback message is stored in the
 * {@code commandFeedback} attribute. If the task creation fails, an error message
 * is stored instead.
 * </p>
 *
 * @see Command
 * @see TaskCommand
 * @see TaskManager
 * @see TaskFactory
 */
public class AddCommand extends Command implements TaskCommand {
    private final TaskManager taskManager;
    private final String taskType;
    private final String details;

    /**
     * Constructs an {@code AddCommand} with the specified task manager, task type, and details.
     *
     * @param taskManager the task manager to which the task will be added
     * @param taskType    the type of task to create (e.g., "todo", "deadline", "event")
     * @param details     the details of the task (e.g., description, due date)
     */
    public AddCommand(TaskManager taskManager, String taskType, String details) {
        this.taskManager = taskManager;
        this.taskType = taskType;
        this.details = details;
    }

    /**
     * Executes the Add command by creating a task and adding it to the task manager.
     * <p>
     * The task is created using the {@link TaskFactory#createTask(String, String)} method.
     * If the task creation is successful, the task is added to the task manager, and a
     * success message is stored in the {@code commandFeedback} attribute. 
     * Otherwise, an error message is stored instead.
     * </p>
     * <p>
     * This method always returns {@code false} to indicate that the program should
     * continue running after executing this command.
     * </p>
     *
     * @return {@code false} to signal that the program should not end
     * @see TaskFactory#createTask(String, String)
     * @see TaskManager#addTask(Task)
     */
    @Override
    public boolean execute() {
        Task task = TaskFactory.createTask(taskType, details);
        commandFeedback = (task == null) ? "Task was not created\n\n" + showHelpString() 
            : taskManager.addTask(task);
        return false;
    }

    /**
     * Returns a help message that includes a hint on the expected format for adding tasks.
     *
     * @return a string containing the help message
     */
    public String showHelpString() {
        return "\tHint: add deadline/event/todo task_details";
    }
}
