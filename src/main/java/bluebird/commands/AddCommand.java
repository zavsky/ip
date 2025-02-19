package bluebird.commands;

import bluebird.TaskFactory;
import bluebird.TaskManager;
import bluebird.UIHandler;
import bluebird.exceptions.IllegalTaskParameterException;
import bluebird.tasks.Task;

public class AddCommand extends Command {
    private final TaskManager taskManager;
    private final UIHandler ui;
    private final String taskType;
    private final String details;

    public AddCommand(TaskManager taskManager, UIHandler ui, String taskType, String details) {
        this.taskManager = taskManager;
        this.ui = ui;
        this.taskType = taskType;
        this.details = details;
    }

    /**
     * Creates a Task via TaskFactory class and
     * adds it to existing ArrayList handled by TaskManager.
     * Prints a success message.
     * @return false to signal that the program should not end.
     */
    @Override
    public boolean execute() {
        try {
            Task task = TaskFactory.createTask(taskType, details);
            taskManager.addTask(task);
            ui.showSuccess("Added task: " + task.getDescription());
        } catch (IllegalTaskParameterException e) {
            ui.showError("Task params for adding '" + taskType + "' are incorrect");
        }
        return false;
    }
}
