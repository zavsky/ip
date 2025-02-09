import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final ArrayList<Task> tasks = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    // protected static State runState;
    // private static Task[] userTasks = new Task[100];
    // private static int userTaskCount = 0;

    // enum State {
    //     LISTEN,
    //     CREATETASK,
    //     LISTTASK,
    //     EXIT
    // }

    public static void main (String[] args) {
        bluebird bb = new bluebird();
        bb.greetHello();

        while (true) {
            String userInput = scanner.nextLine().trim();
            String[] userInputParts = userInput.split(" ", 2);
            String command = userInputParts[0];
            // updateState(userInput);

            clearScreen();

            switch(command) {
                case "list":
                    listTask();
                    break;
                case "add":
                    if (userInputParts.length > 1) addTask(userInputParts[1]);
                    break;
                case "mark":
                    if (userInputParts.length > 1) markTask(Integer.parseInt(userInputParts[1]) - 1);
                    break;
                case "unmark":
                    if (userInputParts.length > 1) unmarkTask(Integer.parseInt(userInputParts[1]) - 1);
                    break;
                case "delete":
                    if (userInputParts.length > 1) deleteTask(Integer.parseInt(userInputParts[1]) - 1);
                    break;
                case "undo":
                    // implement undo last functionality
                    break;
                case "exit":
                    bb.greetGoodbye();
                    return;
                default:
                    System.out.println("Unknown command. Use list, add, mark, unmark or exit");
            }
        }
    }

    // public static void updateState(String userInput) {
    //     switch (userInput.trim().toLowerCase()) {
    //         case "bye": runState = State.EXIT; break;
    //         case "list": runState = State.LISTEN; listTask(); break;
    //         default: runState = State.LISTEN; createTask(userInput); break;
    //     }
    // }

    public static void listTask() {
        printLine();
        System.out.println("    Here's your todo:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("        " + (i + 1) + ". " + tasks.get(i));
        }
        printLine();
    }

    public static void addTask(String description) {
        tasks.add(new Task(description));
        printLine();
        System.out.println("    Created task: " + description);
        printLine();
    }

    public static void markTask(int taskIndex) {
        if (taskIndex >= 0 && taskIndex < tasks.size()) {
            tasks.get(taskIndex).markDone(true);
            printLine();
            System.out.println("    Caw! I've marked the following as done:");
            System.out.println("        " + tasks.get(taskIndex));
        } else {
            System.out.println("Invalid task number");
        }
    }

    public static void unmarkTask(int taskIndex) {
        if (taskIndex >= 0 && taskIndex < tasks.size()) {
            tasks.get(taskIndex).markDone(false);
            printLine();
            System.out.println("    Stop fooling, seize the day!");
            System.out.println("        \"" + tasks.get(taskIndex).getDescription() + "\" is now marked undone");
            printLine();
        } else {
            System.out.println("Invalid task number");
        }
    }

    public static void deleteTask(int taskIndex) {
        if (taskIndex >= 0 && taskIndex < tasks.size()) {
            printLine();
            System.out.println("    One less item on your bucketlist. The following has been deleted:");
            System.out.println("        " + tasks.get(taskIndex));
            printLine();
            tasks.remove(taskIndex);
        } else {
            System.out.println("Invalid task number");
        }
    }

    private static void printLine() {
        System.out.println("    ____________________________________________________________");
        System.out.println("");
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }
}
