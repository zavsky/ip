package bluebird;

import bluebird.commands.Command;

public class Main {
    public static void main (String[] args) {
        Storage storage = new Storage();
        storage.ensureFileExists();
        
        Bluebird bb = new Bluebird();
        TaskManager TaskManager = new TaskManager();
        UIHandler ui = new UIHandler();
        CommandParser parser = new CommandParser(TaskManager, ui);
        bb.greetHello();

        while (true) {
            try {
                String input = ui.getUserInput("Enter command: ");
                bb.clearScreen();
                Command command = parser.parseInput(input);
                if (command.execute()) {
                    break;
                }
            } catch (IndexOutOfBoundsException e) {
                ui.showError(e.getMessage());
            }
        }
    }
}
