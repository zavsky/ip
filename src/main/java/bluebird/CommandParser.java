package bluebird;

import java.security.SecureRandom;

import bluebird.commands.*;
import bluebird.exceptions.IndexOutOfBoundsException;

public class CommandParser {
    private final TaskManager taskManager;
    private final UIHandler ui;

    private static final SecureRandom random = new SecureRandom();

    public CommandParser(TaskManager taskManager, UIHandler ui) {
        this.taskManager = taskManager;
        this.ui = ui;
    }

    public Command parseInput(String input) {
        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();
        String arguments = parts.length > 1 ? parts[1] : "";

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
        //case "undo":
        //case "x":
            // implement undo last functionality
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

        return new AddCommand(taskManager, ui, taskType, details);

        // System.out.println(randomEnum(TaskType.class).name());
        // return new Command();
    }

    /**
     * Taken from stackoverflow.com/questions/1972392/pick-a-random-value-from-an-enum
     * from users Eldelshell and zuddduz.
     * @return
     */
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
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
        try {
            String trimmedArgs = arguments.trim();
            if (trimmedArgs.isEmpty()) {
                ui.displayTasks(taskManager.getTasks());
                trimmedArgs = ui.getUserInput("Enter task number: ");
            }
            return new MarkCommand(taskManager, ui, parseTaskIndex(trimmedArgs), markAsDone);
        } catch (IndexOutOfBoundsException e) {
            ui.showError("Index provided is out of bounds");
            return new Command();
        }
    }

    private Command parseDeleteCommand(String arguments) {
        try {
            String trimmedArgs = arguments.trim();
            if (trimmedArgs.isEmpty()) {
                ui.displayTasks(taskManager.getTasks());
                trimmedArgs = ui.getUserInput("Enter task number to delete: ");
            }
            return new DeleteCommand(taskManager, ui, parseTaskIndex(trimmedArgs));
        } catch (IndexOutOfBoundsException e) {
            ui.showError("Index provided is out of bounds");
            return new Command();
        }
    }

    private int parseTaskIndex(String input) throws IndexOutOfBoundsException {
        int index = Integer.parseInt(input.trim()) - 1;
        // check index out-of-bounds
        if (index < 0 || index >= taskManager.getTasks().size()) {
            throw new IndexOutOfBoundsException();
        }
        return index;
    }
}
