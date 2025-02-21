package bluebird.commands;

import bluebird.TaskManager;

public class ListCommand extends Command {
    private final TaskManager taskManager;

    public ListCommand(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    /**
     * Calls UIHandler to display all saved Tasks handled by TaskManager.
     * @return false to signal that the program should not end.
     */
    @Override
    public boolean execute() {
        commandFeedback = taskManager.getPrintableTasks();
        return false;
    }
}
