package bluebird;

import bluebird.commands.*;

public class CommandParser {
    private final TaskManager taskManager;
    private final UIHandler ui;
    private String undoCommand;
    
        public CommandParser(TaskManager taskManager, UIHandler ui) {
            this.taskManager = taskManager;
            this.ui = ui;
        }
    
        public Command parseInput(String input) {
            String[] parts = input.split(" ", 2);
            String command = parts[0].toLowerCase();
            String arguments = parts.length > 1 ? parts[1] : "";
            System.out.println(undoCommand);
    
            switch (command) {
            case "list":
            case "l":
                return new ListCommand(taskManager, ui);
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
                return parseInput(undoCommand);
            case "help":
            case "h":
                return new HelpCommand(ui);
            case "exit":
            case "e":
                //bb.greetGoodbye();
                return new ExitCommand();
            default:
                System.out.println("Unknown command.");
                return new HelpCommand(ui);
            }
        }
    
        private Command parseAddCommand(String arguments) {
            String trimmedArgs = arguments.trim();
            String[] parts = trimmedArgs.split(" ", 2);
            String taskType = parts[0].toLowerCase();
    
            if (!isValidTaskType(taskType)) {
                String input = ui.getUserInput("Add event, deadline or todo? ").trim();
                parts = input.split(" ", 2);
                taskType = parts[0].toLowerCase();
                
                if (!isValidTaskType(taskType)) {
                    return new Command();
                }
            }
    
            String details = (parts.length > 1) ? parts[1] : ui.getUserInput("Enter details: ");
    
            if (details.trim().isEmpty()) {
                return new Command();
            }
            // very rudimentary implementation of undo last command
            undoCommand = "d " + (taskManager.getTaskCount() + 1);

        return new AddCommand(taskManager, ui, taskType, details);

        // System.out.println(randomEnum(TaskType.class).name());
        // return new Command();
    }

    enum TaskType {
        DEADLINE, D,
        EVENT, E,
        TODO, T
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
        String trimmedArgs = arguments.trim();
        if (trimmedArgs.isEmpty()) {
            // ui.displayTasks(taskManager.getTasks());
            ui.displayPrintableTasks(taskManager.getPrintableTasks());
            if (taskManager.isEmpty()) {
                return new Command();
            }
            trimmedArgs = ui.getUserInput("Enter task number: ").trim();
        }
        if (trimmedArgs.isEmpty()) {
            return new Command();
        }
        int taskIndex = parseTaskIndex(trimmedArgs);
        if (taskIndex == -1) {
            ui.showError("Index to mark does not make sense");
            return new Command();
        }
        // very rudimentary implementation of undo last command
        undoCommand = (markAsDone ? "u " : "m ") + (taskIndex + 1);
        return new MarkCommand(taskManager, ui, taskIndex, markAsDone);
    }

    private Command parseDeleteCommand(String arguments) {
        String trimmedArgs = arguments.trim();
        if (trimmedArgs.isEmpty()) {
            // ui.displayTasks(taskManager.getTasks());
            ui.displayPrintableTasks(taskManager.getPrintableTasks());
            if (taskManager.isEmpty()) {
                return new Command();
            }
            trimmedArgs = ui.getUserInput("Enter task number to delete: ").trim();
        }
        if (trimmedArgs.isEmpty()) {
            return new Command();
        }
        int taskIndex = parseTaskIndex(trimmedArgs);
        if (taskIndex == -1) {
            ui.showError("Index for deletion does not make sense");
            return new Command();
        }
        // very rudimentary implementation of undo last command
        undoCommand = "a " + taskManager.getUndoCommand(taskIndex);
        return new DeleteCommand(taskManager, ui, taskIndex);
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
