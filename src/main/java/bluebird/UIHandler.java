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
        String messageFormat;
        
        switch (type) {
        case ERROR:
            messageFormat = "\t" + bb.curse() + "\tERROR:\t";
            break;
        case SUCCESS:
            messageFormat = "\t" + bb.scream() + "\n\t";
            break;
        case SHOWTASK:
            messageFormat = "\tHere's your todo:\n\n";
            break;
        case MARK:
            messageFormat = "\tSelect task to mark:\n\n";
            break;
        case UNMARK:
            messageFormat = "\tSelect task to unmark:\n\n";
            break;
        case DELETE:
            messageFormat = "\tSelect task to delete:\n\n";
            break;
        case INFO:
        default:
            messageFormat = "\t";
        }

        printLine();
        System.out.println(messageFormat + message);
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
        System.out.println("\t::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::" + 
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
        String userResponse = getUserInput(queryString).trim();
        if (!userResponse.isEmpty()) {
            return userResponse;
        }
        userResponse = getUserInput("Do you want to leave this field empty? y/N  ");
        
        if (userResponse.equalsIgnoreCase("y")) {
            return "?";
        }
        throw new IllegalTaskParameterException();
    }
}
