package bluebird;
import java.util.Scanner;

import bluebird.exceptions.IllegalTaskParameterException;

public class UIHandler {
    private final Scanner scanner = new Scanner(System.in);
    private final Bluebird bb = new Bluebird();

    public void showMessage(String message) {
        showMessage(MessageType.INFO, message);
    }
    
    public void showMessage(MessageType type, String message) {
        String messageFormat = MessageType.getMessageFormat(type);

        printLine();
        System.out.printf(messageFormat + message + "\n\n");
        printLine();
    }

    public String getUserInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public void showHello() {
        clearScreen();
        System.out.println(bb.greetHello());
    }

    public void showGoodbye() {
        clearScreen();
        System.out.println(bb.enthusiastic());
    }

    public void showConfused() {
        clearScreen();
        System.out.println(bb.confused());
    }

    public void showHelpless() {
        System.out.println(bb.helpless());
    }

    public void showTasks(String taskString) {
        if (taskString.isEmpty()) {
            showMessage("You have no tasks!");
        } else {
            showMessage(MessageType.SHOWTASK, taskString);
        }
    }

    public void showTasks(String taskString, MessageType messageType) {
        if (taskString.isEmpty()) {
            showMessage("You have no tasks!");
        } else {
            showMessage(messageType, taskString);
        }
    }

    private static void printLine() {
        System.out.println("\t:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::" + 
            System.lineSeparator());
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    /**
     * 
     * @param queryString
     * @return trimmed user input if not empty
     * @throws IllegalTaskParameterException
     */
    public String promptUser(String queryString) throws IllegalTaskParameterException {
        clearScreen();
        String userResponse = getUserInput(queryString + "\n").trim();
        if (!userResponse.isEmpty()) {
            return userResponse;
        }
        if (confirmAction("leave this field empty")) {
            return "?";
        }
        throw new IllegalTaskParameterException();
    }

    public boolean confirmAction(String action) {
        String userResponse = getUserInput("Are you sure to " + action + "? y/N  ");
        clearScreen();
        if (userResponse.equalsIgnoreCase("y")) {
            return true;
        }
        return false;
    }
}
