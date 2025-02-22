package bluebird.commands;

import bluebird.TaskManager;

public class ListCommand extends Command {
    private final TaskManager taskManager;

    public ListCommand(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    /**
     * Adds String-formatted task list to @attribute commandFeedback.
     * @return false to signal that the program should not end.
     */
    @Override
    public boolean execute() {
        commandFeedback = taskManager.getPrintableTasks();
        return false;
    }
}
