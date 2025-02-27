package bluebird.commands;

import bluebird.TaskManager;

public class FindCommand extends Command {
    private final TaskManager taskManager;
    private final String key;

    public FindCommand(TaskManager taskManager, String key) {
        this.taskManager = taskManager;
        this.key = key;
    }

    /**
     * Adds String-formatted key-matching task list to @attribute commandFeedback.
     * @return false to signal that the program should not end.
     */
    @Override
    public boolean execute() {
        commandFeedback = taskManager.getPrintableTasks(key);
        return false;
    }
}
