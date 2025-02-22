package bluebird.commands;

import bluebird.TaskManager;

public class UndoCommand extends Command {
    private final TaskManager taskManager;

    public UndoCommand(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    /**
     * Undoes the last user command via TaskManager.
     * Does nothing if there is no last user command.
     * @return false to signal that the program should not end.
     */
    @Override
    public boolean execute() {
        commandFeedback = taskManager.undoCommand();
        return false;
    }
}
