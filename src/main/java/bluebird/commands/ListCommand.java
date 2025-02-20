package bluebird.commands;

import bluebird.TaskManager;
import bluebird.UIHandler;

public class ListCommand extends Command {
    private final TaskManager taskManager;
    private final UIHandler ui;

    public ListCommand(TaskManager taskManager, UIHandler ui) {
        this.taskManager = taskManager;
        this.ui = ui;
    }

    /**
     * Calls UIHandler to display all saved Tasks handled by TaskManager.
     * @return false to signal that the program should not end.
     */
    @Override
    public boolean execute() {
        // ui.displayTasks(taskManager.getTasks());
        ui.displayPrintableTasks(taskManager.getPrintableTasks());
        return false;
    }
}
