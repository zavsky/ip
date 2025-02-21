package bluebird;

import bluebird.commands.*;
import bluebird.commands.taskcommands.*;
import bluebird.tasks.TaskType;

public class CommandParser {
    private final TaskManager taskManager;
    private final UIHandler ui;
    
    public CommandParser(TaskManager taskManager, UIHandler ui) {
        this.taskManager = taskManager;
        this.ui = ui;
    }
    
    public Command parseInput(String input) {
        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();
        String arguments = parts.length > 1 ? parts[1].trim() : "";

        switch (command) {
        case "list":
        case "l":
            return new ListCommand(taskManager);
        case "add":
        case "a":
            return parseAddCommand(arguments);
        case "mark":
        case "m":
            return parseMarkCommand(arguments, true);
        case "unmark":
        case "u":
            return parseMarkCommand(arguments, false);
        case "delete":
        case "d":
            return parseDeleteCommand(arguments);
        case "undo":
        case "z":
            return new UndoCommand(taskManager);
        case "help":
        case "h":
            return new HelpCommand();
        case "exit":
        case "e":
            return new ExitCommand();
        default:
            ui.showConfused();
            return new HelpCommand();
        }
    }
    
    private Command parseAddCommand(String arguments) {
        String args = arguments;
        String[] parts = args.split(" ", 2);
        String taskType = parts[0].toLowerCase();

        if (!isValidTaskType(taskType)) {
            String input = ui.getUserInput("Add event, deadline or todo? ").trim();
            parts = input.split(" ", 2);
            taskType = parts[0].toLowerCase();
            
            if (!isValidTaskType(taskType)) {
                return null;
            }
        }

        String details = (parts.length > 1) ? parts[1] : ui.getUserInput("Enter details: ");

        if (details.trim().isEmpty()) {
            return null;
        }

        return new AddCommand(taskManager, taskType, details);
    }

    private boolean isValidTaskType(String args) {
        try {
            TaskType.valueOf(args.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private Command parseMarkCommand(String arguments, boolean markAsDone) {
        String args = arguments;
        if (args.isEmpty()) {
            ui.showTasks(taskManager.getPrintableTasks(), (markAsDone ? MessageType.MARK : MessageType.UNMARK));
            if (taskManager.isEmpty()) {
                return null;
            }
            args = ui.getUserInput("Enter task number: ").trim();
        }
        if (args.isEmpty()) {
            return null;
        }
        int taskIndex = parseTaskIndex(args);
        if (taskIndex == -1) {
            ui.showMessage(MessageType.ERROR, "Index to mark does not make sense");
            return new EmptyCommand();
        }
        return new MarkCommand(taskManager, taskIndex, markAsDone);
    }

    private Command parseDeleteCommand(String arguments) {
        String args = arguments;
        if (args.isEmpty()) {
            ui.showTasks(taskManager.getPrintableTasks(), MessageType.DELETE);
            if (taskManager.isEmpty()) {
                return null;
            }
            args = ui.getUserInput("Enter task number to delete: ").trim();
        }
        if (args.isEmpty()) {
            return null;
        }
        int taskIndex = parseTaskIndex(args);
        if (taskIndex == -1) {
            ui.showMessage(MessageType.ERROR, "Index for deletion does not make sense");
            return new EmptyCommand();
        }
        return new DeleteCommand(taskManager, taskIndex);
    }

    private int parseTaskIndex(String input) {
        try {
            int index = Integer.parseInt(input.trim()) - 1;
            // check index within bounds
            if (index >= 0 && index < taskManager.getTaskCount()) {
                return index;
            }
        } catch (NumberFormatException e) {
            // invalid number format
        }
        return -1;
    }
}
