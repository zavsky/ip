package bluebird.commands.taskcommands;

import bluebird.TaskFactory;
import bluebird.TaskManager;
import bluebird.commands.Command;
import bluebird.exceptions.IllegalTaskParameterException;
import bluebird.tasks.Task;

public class AddCommand extends Command implements TaskCommand {
    private final TaskManager taskManager;
    private final String taskType;
    private final String details;

    public AddCommand(TaskManager taskManager, TaskFactory taskFactory, String taskType, String details) {
        this.taskManager = taskManager;
        this.taskType = taskType;
        this.details = details;
    }

    /**
     * Creates a Task via TaskFactory with user-given inputs.
     * @attribute taskType and details must be non-empty,
     * but its validity is unchecked.
     * Adds a message to the @attribute commandFeedback if successful.
     * @return false to signal that the program should not end.
     * @throws exception if Task creation was unsuccessful.
     */
    @Override
    public boolean execute() throws IllegalTaskParameterException {
        Task task = TaskFactory.createTask(taskType, details);
        commandFeedback = taskManager.addTask(task);
        return false;
    }

    public String showHelpString() {
        return "Syntax:\nadd deadline/event/todo task_details"; // implement THIS better
    }
}
