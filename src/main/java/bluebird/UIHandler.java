package bluebird;
import java.util.List;
import java.util.Scanner;

import bluebird.tasks.Task;

public class UIHandler {
    private final Scanner scanner = new Scanner(System.in);
    
    public void showError(String message) {
        printLine();
        System.out.println("\tERROR:\t" + message);
        printLine();
    }

    public void showSuccess(String message) {
        printLine();
        System.out.println("\t" + message);
        printLine();
    }

    public void showMessage(String message) {
        printLine();
        System.out.println(message);
        printLine();
    }

    public String getUserInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public void displayTasks(List<Task> tasks) {
        if (tasks.size() == 0) {
            System.out.println("You have no tasks!");
            return;
        }
        printLine();
        System.out.println("\tHere's your todo:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.printf("\t\t%d. %s%n", i + 1, tasks.get(i));
        }
        printLine();
    }

    private static void printLine() {
        System.out.println("\t____________________________________________________________");
        System.out.println("");
    }
}
