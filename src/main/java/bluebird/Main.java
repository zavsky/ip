package bluebird;

import bluebird.commands.Command;
import bluebird.commands.EmptyCommand;
import bluebird.commands.HelpCommand;
import bluebird.commands.ListCommand;

public class Main {
    public static void main (String[] args) {
        TaskManager taskManager = new TaskManager();
        UIHandler ui = new UIHandler();
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
            } else if (command instanceof HelpCommand) {
                ui.showMessage(MessageType.INFO, command.commandFeedback);
            } else {
                // Feedbacks a success message if executed a task modification command
                ui.showMessage(MessageType.SUCCESS, command.commandFeedback);
            }
        }
    }
}
