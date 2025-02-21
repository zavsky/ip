package bluebird;

import bluebird.commands.Command;
import bluebird.commands.EmptyCommand;
import bluebird.commands.HelpCommand;
import bluebird.commands.ListCommand;
import bluebird.exceptions.IllegalTaskParameterException;

// todo implement marking and unmarking and deleting multiple tasks in one command
public class Main {
    public static void main (String[] args) {
        Storage storage = new Storage();
        storage.ensureFileExists();
        
        TaskManager TaskManager = new TaskManager();
        UIHandler ui = new UIHandler();
        CommandParser parser = new CommandParser(TaskManager, ui);
        ui.showHello();

        while (true) {
            try {
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
                    ui.clearScreen();
                    ui.showGoodbye();
                    break;
                }

                if (command instanceof ListCommand) {
                    ui.showTasks(command.commandFeedback);
                } else if (command instanceof HelpCommand) {
                    ui.showMessage(MessageType.INFO, command.commandFeedback);
                } else {
                    // Feedbacks a success message if executed a task modification command
                    ui.showMessage(MessageType.SUCCESS, command.commandFeedback);
                }

            } catch (IllegalTaskParameterException e) {
                ui.showMessage(MessageType.ERROR, "One or more parameters for adding task is incorrect");
            }
        }
    }
}
