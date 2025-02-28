package bluebird;

import bluebird.commands.Command;
import bluebird.commands.EmptyCommand;
import bluebird.commands.FindCommand;
import bluebird.commands.HelpCommand;
import bluebird.commands.ListCommand;

/**
 * Starting point of the Bluebird application. Runs in a loop calling for user input, 
 * parses them and updates the task list appropriately. It prints a summary of its 
 * activity and informs the user if the action was successful. Quits upon capturing 
 * the user-specified exit command.
 */
public class Main {
    public static void main (String[] args) {
        TaskManager taskManager = new TaskManager();
        UIHandler ui = new UIHandler();
        @SuppressWarnings("unused")
        TaskFactory taskFactory = new TaskFactory(ui);
        CommandParser parser = new CommandParser(taskManager, ui);
        ui.showHello();

        while (true) {
            String input = ui.getUserInput("Enter command: ");
            ui.clearScreen();
            Command command = parser.parseInput(input);

            if (command == null) {
                ui.showConfused();
                continue;
            }

            if (command instanceof EmptyCommand) {
                continue;
            }

            if (command.execute()) {
                ui.showGoodbye();
                break;
            }

            if (command instanceof ListCommand) {
                ui.showTasks(command.commandFeedback);
            } else if (command instanceof FindCommand) {
                ui.showTasks(command.commandFeedback, MessageType.FINDTASK);
            } else if (command instanceof HelpCommand) {
                ui.showMessage(MessageType.INFO, command.commandFeedback);
            } else {
                // Feedbacks a success message if executed a task modification command
                ui.showMessage(MessageType.SUCCESS, command.commandFeedback);
            }
        }
    }
}
