package bluebird;
// import java.util.ArrayList;
// import java.util.Scanner;

// import org.w3c.dom.events.EventException;

import bluebird.commands.Command;
// import bluebird.tasks.Deadline;
// import bluebird.tasks.Event;
// import bluebird.tasks.Task;
// import bluebird.tasks.ToDo;

public class Main {
    // private static final ArrayList<Task> tasks = new ArrayList<>();
    // private static final Scanner scanner = new Scanner(System.in);
    public static void main (String[] args) {
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
            // String userInput = getUserInput();
            // String[] userInputParts = userInput.split(" ", 2);
            // String command = userInputParts[0];

            // clearScreen();

            // switch(command) {
            // case "list":
            // case "l":
            //     listTask();
            //     break;
            // case "add":
            // case "a":
            //     if (userInputParts.length > 1) {
            //         addTask(userInputParts[1]);
            //     } else {
            //         addTask();
            //     }
            //     break;
            // case "mark":
            // case "m":
            //     if (userInputParts.length > 1) {
            //         markTask(Integer.parseInt(userInputParts[1]) - 1);
            //     } else {
            //         markTask();
            //     }
            //     break;
            // case "unmark":
            // case "u":
            //     if (userInputParts.length > 1) {
            //         unmarkTask(Integer.parseInt(userInputParts[1]) - 1);
            //     } else {
            //         unmarkTask();
            //     }
            //     break;
            // case "delete":
            // case "d":
            //     if (userInputParts.length > 1) {
            //         deleteTask(Integer.parseInt(userInputParts[1]) - 1);
            //     } else {
            //         deleteTask();
            //     }
            //     break;
            // case "undo":
            // case "x":
            //     // implement undo last functionality
            //     break;
            // case "help":
            // case "h":
            //     // implement help string
            //     break;
            // case "exit":
            // case "e":
            //     bb.greetGoodbye();
            //     return;
            // default:
            //     System.out.println("Unknown command. Use list, add, mark, unmark or exit");
            // }
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

//     public static void listTask() {
//         printLine();
//         System.out.println("    Here's your todo:");
//         for (int i = 0; i < tasks.size(); i++) {
//             System.out.println("        " + (i + 1) + ". " + tasks.get(i));
//         }
//         printLine();
//     }

//     public static void addTask() {
//         clearScreen();
//         System.out.println("Add deadline, todo or event?");
//         String userInput = scanner.nextLine().trim();
//         addTask(userInput);
//     }

//     public static void addTask(String input) {
//         String[] inputParts = input.split(" ", 2);
//         String command = inputParts[0];

//         switch (command) {
//         case "deadline":
//         case "d":
//             if (inputParts.length > 1) {
//                 addDeadline(inputParts[1]);
//             } else {
//                 addDeadline();
//             }
//             break;
//         case "todo":
//         case "t":
//             if (inputParts.length > 1) {
//                 addToDo(inputParts[1]);
//             } else {
//                 addToDo();
//             }
//             break;
//         case "event":
//         case "e":
//             if (inputParts.length > 1) {
//                 addEvent(inputParts[1]);
//             } else {
//                 addEvent();
//             }
//             break;
//         case "exit":
//         case "quit":
//             return;
//         default:
//             System.out.println("Unknown add .. command. Use deadline, todo or event");
//             addTask();
//         }
        
//     }

//     private static void addDeadline() {
//         clearScreen();
//         System.out.println("Deadline /by?");
//         String userInput = getUserInput();
//         addDeadline(userInput);
//     }

//     private static void addDeadline(String input) {
//         String[] inputParts = input.split("/by", 2);
        
//         if (inputParts.length < 2) {
//             return;
//         }
        
//         String description = inputParts[0].trim();
//         String by = inputParts[1].trim();

//         Deadline newDeadLine = new Deadline(description, by);
//         tasks.add(newDeadLine);
//         printAddSuccess(newDeadLine);
//     }

//     private static void addEvent() {
//         clearScreen();
//         System.out.println("Event /from /to?");
//         String userInput = getUserInput();
//         addEvent(userInput);
//     }

//     private static void addEvent(String input) {
//         // improve description, from and to checking, making sure the order is not reversed
//         String[] inputParts = input.split("/from|/to", 3);
//         String description = inputParts[0].trim();
//         String from = inputParts[1].trim();
//         String to = inputParts[2].trim();

//         Event newEvent = new Event(description, from, to);
//         tasks.add(newEvent);
//         printAddSuccess(newEvent);
//     }

//     private static void addToDo() {
//         clearScreen();
//         System.out.println("Todo?");
//         String userInput = getUserInput();
//         addToDo(userInput);
//     }

//     private static void addToDo(String description) {
//         ToDo newToDo = new ToDo(description.trim());
//         tasks.add(newToDo);
//         printAddSuccess(newToDo);
//     }

//     private static void printAddSuccess(Task tasks) {
//         clearScreen();
//         printLine();
//         System.out.println("    Created " + tasks.getClass().getSimpleName() + ": " + tasks.getDescription());
//         printLine();
//     }

//     public static void markTask() {
//         System.out.println("Mark which as done?");
//         listTask();
//         String userInput = getUserInput();

//         // check if user has input an integer
//         if (!userInput.matches("-?\\d+")) {
//             System.out.println("Not an integer");
//             return;
//         }

//         int taskIndex = Integer.parseInt(userInput) - 1;
//         markTask(taskIndex);
//     }

//     public static void markTask(int taskIndex) {
//         if (taskIndex >= 0 && taskIndex < tasks.size()) {
//             clearScreen();
//             tasks.get(taskIndex).markDone(true);
//             printLine();
//             System.out.println("    Caw! I've marked the following as done:");
//             System.out.println("        " + tasks.get(taskIndex));
//         } else {
//             System.out.println("Invalid task number");
//         }
//     }

//     public static void unmarkTask() {
//         System.out.println("Mark which as not done?");
//         listTask();
//         String userInput = getUserInput();

//         // check if user has input an integer
//         if (!userInput.matches("-?\\d+")) {
//             System.out.println("Not an integer");
//             return;
//         }

//         int taskIndex = Integer.parseInt(userInput) - 1;
//         unmarkTask(taskIndex);
//     }

//     public static void unmarkTask(int taskIndex) {
//         if (taskIndex >= 0 && taskIndex < tasks.size()) {
//             clearScreen();
//             tasks.get(taskIndex).markDone(false);
//             printLine();
//             System.out.println("    Stop fooling, seize the day!");
//             System.out.println("        \"" + tasks.get(taskIndex).getDescription() + "\" is now marked undone");
//             printLine();
//         } else {
//             System.out.println("Invalid task number");
//         }
//     }

//     public static void deleteTask() {
//         System.out.println("Delete which task?");
//         listTask();
//         String userInput = getUserInput();

//         // check if user has input an integer
//         if (!userInput.matches("-?\\d+")) {
//             System.out.println("Not an integer");
//             return;
//         }

//         int taskIndex = Integer.parseInt(userInput) - 1;
//         deleteTask(taskIndex);
//     }

//     public static void deleteTask(int taskIndex) {
//         if (taskIndex >= 0 && taskIndex < tasks.size()) {
//             clearScreen();
//             printLine();
//             System.out.println("    One less item on your bucketlist. The following has been deleted:");
//             System.out.println("        " + tasks.get(taskIndex));
//             printLine();
//             tasks.remove(taskIndex);
//         } else {
//             System.out.println("Invalid task number");
//         }
//     }

//     private static String getUserInput() {
//         return scanner.nextLine().trim();
//     }

//     private static void printLine() {
//         System.out.println("    ____________________________________________________________");
//         System.out.println("");
//     }

//     public static void clearScreen() {
//         System.out.print("\033[H\033[2J");  
//         System.out.flush();  
//     }
// }
