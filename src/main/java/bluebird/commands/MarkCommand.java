package bluebird.commands;

import bluebird.TaskManager;
import bluebird.UIHandler;

public class MarkCommand extends Command {
    private final TaskManager taskManager;
    private final UIHandler ui;
    private final int taskIndex;
    private final boolean markAsDone;

    public MarkCommand(TaskManager taskManager, UIHandler ui, int taskIndex, boolean markAsDone) {
        this.taskManager = taskManager;
        this.ui = ui;
        this.taskIndex = taskIndex;
        this.markAsDone = markAsDone;
    }

    /**
     * Toggles the Task completion (markAsDone) flag according to user input.
     * Prints a success message.
     * @return false to signal that the program should not end.
     */
    @Override
    public boolean execute() {
        taskManager.markTask(taskIndex, markAsDone);
        String status = markAsDone ? "marked as done" : "marked as not done";
        ui.showSuccess("Task " + (taskIndex + 1) + " has been " + status);
        return false;
    }
}
