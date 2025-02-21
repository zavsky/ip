package bluebird.commands;

import bluebird.TaskManager;

public class UndoCommand extends Command {
    private final TaskManager taskManager;

    public UndoCommand(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public boolean execute() {
        commandFeedback = taskManager.undoCommand();
        return false;
    }
}
