package bluebird.commands;

import bluebird.TaskManager;
import bluebird.UIHandler;
import bluebird.tasks.Task;

public class DeleteCommand extends Command {
    private final TaskManager taskManager;
    private final UIHandler ui;
    private final int taskIndex;
    
    public DeleteCommand(TaskManager taskManager, UIHandler ui, int taskIndex) {
        this.taskManager = taskManager;
        this.ui = ui;
        this.taskIndex = taskIndex;
    }

    /**
     * Delete a task via the index specified by the user.
     * Prints a success message.
     * @return false to signal that the program should not end.
     */
    @Override
    public boolean execute() {
        String deletedTask = taskManager.getTaskDescription(taskIndex);
        taskManager.deleteTask(taskIndex);
        ui.showSuccess("Deleted task: " + deletedTask);
        return false;
    }
}
